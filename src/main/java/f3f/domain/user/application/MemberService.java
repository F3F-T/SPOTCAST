package f3f.domain.user.application;

import f3f.domain.model.LoginType;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO.SaveRequest;
import f3f.domain.user.exception.*;
import f3f.global.encrypt.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static f3f.domain.user.dto.MemberDTO.*;
import static f3f.global.util.MemberConstants.EMAIL;
import static f3f.global.util.MemberConstants.LOGIN_STATUS;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    //전화번호 인증 추가



    private final MemberRepository memberRepository;
    private final EncryptionService encryptionService;
    private final HttpSession session;


    /**
     * 회원가입
     * @param saveRequest
     * @return
     */
    public Long saveMember(SaveRequest saveRequest){
        if(emailDuplicateCheck(saveRequest.getEmail())){
            throw new DuplicateEmailException();
        }

        if(nicknameDuplicateCheck(saveRequest.getNickname())){
            throw new DuplicateNicknameException();
        }

        saveRequest.passwordEncryption(encryptionService);
        Member member = saveRequest.toEntity();
        memberRepository.save(member);

        return member.getId();
    }

    public void deleteMember(String email, String password){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(!memberRepository.existsByEmailAndPassword(email, encryptionService.encrypt(password))){
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.deleteByEmail(email);
    }
    /**
     * 로그인
     * @param loginRequest
     */
    @Transactional(readOnly = true)
    public void login(LoginRequest loginRequest){
        existsByEmailAndPassword(loginRequest);
        String email = loginRequest.getEmail();
        setLoginMemberType(email);
        session.setAttribute(EMAIL,email);
    }

    /**
     * 로그아웃
     */
    @Transactional(readOnly = true)
    public void logout(){
        session.removeAttribute(EMAIL);
        session.removeAttribute(LOGIN_STATUS);
    }

    /**
     * 회원 정보 조회
     * @param email
     * @return
     */
    public MemberInfoDTO findMyPageInfo(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다.")).toFindMemberDto();
    }

    /**
     * 비밀번호 수정 - 로그인된 상태인 경우
     * @param request
     */
    public void updatePassword(UpdatePasswordRequest request){
        request.passwordEncryption(encryptionService);
        String beforePassword = request.getBeforePassword();
        String afterPassword = request.getAfterPassword();


        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(!member.getLoginType().equals(LoginType.GENERAL_LOGIN)){
            throw new NotGeneralLoginType("비밀번호 변경이 불가능합니다.");
        }

        if(!memberRepository.existsByEmailAndPassword(request.getEmail(),beforePassword)){
            throw new UnauthenticatedMemberException("잘못된 정보입니다.");
        }

        member.updatePassword(afterPassword);
    }

    /**
     * 비밀번호 수정 - 로그인되지 않은 경우(본인 인증 이후)
     * @param request
     */
    public void updatePasswordByForgot(UpdatePasswordRequest request){
        request.passwordEncryption(encryptionService);
        String email = request.getEmail();
        String afterPassword = request.getAfterPassword();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(!member.getLoginType().equals(LoginType.GENERAL_LOGIN)){
            throw new NotGeneralLoginType(member.getLoginType().name()+" 로그인으로 회원가입 되어있습니다.");
        }

        member.updatePassword(afterPassword);

    }


    /**
     * 닉네임 수정
     * @param saveRequest
     */
    public void updateNickname(UpdateNicknameRequest saveRequest){

        String email = saveRequest.getEmail();
        String nickname = saveRequest.getNickname();


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(nicknameDuplicateCheck(saveRequest.getNickname())){
            throw new DuplicateNicknameException("중복된 닉네임은 사용할 수 없습니다.");
        }

        member.updateNickname(nickname);
    }

    /**
     * information 변경
     * @param saveRequest
     */
    public void updateInformation(UpdateInformationRequest saveRequest){

        String email = saveRequest.getEmail();
        String information = saveRequest.getInformation();


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException());


        member.updateInformation(information);
    }


    @Transactional(readOnly = true)
    public void setLoginMemberType(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
        session.setAttribute(LOGIN_STATUS, member.getLoginMemberType());
    }

    @Transactional(readOnly = true)
    public void existsByEmailAndPassword(LoginRequest loginRequest) {
        loginRequest.passwordEncryption(encryptionService);
        if(!memberRepository.existsByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())){
            throw new MemberNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional(readOnly = true)
    public boolean emailDuplicateCheck(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean nicknameDuplicateCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
