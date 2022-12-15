package f3f.domain.user.application;

import f3f.domain.model.LoginType;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO.MemberSaveRequestDto;
import f3f.domain.user.dto.TokenDTO.TokenRequestDTO;
import f3f.domain.user.dto.TokenDTO.TokenResponseDTO;
import f3f.domain.user.exception.*;
import f3f.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static f3f.domain.user.dto.MemberDTO.*;
import static f3f.global.constants.MemberConstants.EMAIL;
import static f3f.global.constants.MemberConstants.LOGIN_STATUS;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    //전화번호 인증 추가


    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final HttpSession session;


    /**
     * 회원가입
     * @param saveRequest
     * @return
     */
    public Long saveMember(MemberSaveRequestDto saveRequest){
        if(emailDuplicateCheck(saveRequest.getEmail())){
            throw new DuplicateEmailException("이미 가입되어 있는 이메일입니다.");
        }

        if(nicknameDuplicateCheck(saveRequest.getNickname())){
            throw new DuplicateNicknameException("이미 가입되어 있는 닉네임입니다.");
        }

        saveRequest.passwordEncryption(passwordEncoder);
        Member member = saveRequest.toEntity();
        memberRepository.save(member);

        return member.getId();
    }

    public void deleteMember(MemberDeleteRequestDto deleteRequest){
        Member member = memberRepository.findByEmail(deleteRequest.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        String email = deleteRequest.getEmail();
        if(!memberRepository.existsByEmailAndPassword(email, deleteRequest.passwordEncryption(passwordEncoder))){
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.deleteByEmail(email);
    }
    /**
     * 로그인
     * @param loginRequest
     */
    @Transactional(readOnly = true)
    public TokenResponseDTO login(MemberLoginRequestDto loginRequest){
        // 로그인 정보로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        //비밀번호 체크 loginRequest 를 authenticationToken 형태로 바꿈
        //authenticate 가 실행될 때 UserDetailsService 에 만들어둔 loadUserByUsername 가 호출되어 DB 의 유저 정보와 일치하는지 확인
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //jwt 토큰 생성
        TokenResponseDTO tokenResponseDTO = tokenProvider.generateTokenDto(authentication);

        session.setAttribute("refreshToken", tokenResponseDTO.getRefreshToken());

        return tokenResponseDTO;

//        existsByEmailAndPassword(loginRequest);
//        String email = loginRequest.getEmail();
//        setLoginMemberType(email);
//        session.setAttribute(EMAIL,email);
    }

    @Transactional
    public TokenResponseDTO reissue(TokenRequestDTO tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        String refreshToken = (String)session.getAttribute("refreshToken");
        if(refreshToken == null){
            throw new RuntimeException("로그아웃 된 사용자입니다.");
        }

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenResponseDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        session.setAttribute("refreshToken",tokenDto.getRefreshToken());

        // 토큰 발급
        return tokenDto;
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
     * @param memberId
     * @return
     */
    public MemberInfoResponseDto findMyPageInfo(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다.")).toFindMemberDto();
    }

    /**
     * 비밀번호 수정 - 로그인된 상태인 경우
     * @param updatePasswordRequest
     */
    public void updatePassword(MemberUpdatePasswordRequestDto updatePasswordRequest){
        updatePasswordRequest.passwordEncryption(passwordEncoder);

        String beforePassword = updatePasswordRequest.getBeforePassword();
        String afterPassword = updatePasswordRequest.getAfterPassword();


        Member member = memberRepository.findByEmail(updatePasswordRequest.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(!memberRepository.existsByEmailAndPassword(updatePasswordRequest.getEmail(),beforePassword)){
            throw new UnauthenticatedMemberException("잘못된 정보입니다.");
        }

        if(!member.getLoginType().equals(LoginType.GENERAL_LOGIN)){
            throw new NotGeneralLoginType("비밀번호 변경이 불가능합니다.");
        }



        member.updatePassword(afterPassword);
    }

    /**
     * 비밀번호 수정 - 로그인되지 않은 경우(본인 인증 이후)
     * @param updatePasswordRequest
     */
    public void updatePasswordByForgot(MemberUpdatePasswordRequestDto updatePasswordRequest){
        updatePasswordRequest.passwordEncryption(passwordEncoder);

        String email = updatePasswordRequest.getEmail();
        String afterPassword = updatePasswordRequest.getAfterPassword();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(!member.getLoginType().equals(LoginType.GENERAL_LOGIN)){
            throw new NotGeneralLoginType(member.getLoginType().name()+" 로그인으로 회원가입 되어있습니다.");
        }

        member.updatePassword(afterPassword);

    }


    /**
     * 닉네임 수정
     * @param updateNicknameRequest
     */
    public void updateNickname(MemberUpdateNicknameRequestDto updateNicknameRequest){

        String email = updateNicknameRequest.getEmail();
        String nickname = updateNicknameRequest.getNickname();


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if(nicknameDuplicateCheck(updateNicknameRequest.getNickname())){
            throw new DuplicateNicknameException("중복된 닉네임은 사용할 수 없습니다.");
        }

        member.updateNickname(nickname);
    }

    /**
     * information 변경
     * @param updateInformationRequest
     */
    public void updateInformation(MemberUpdateInformationRequestDto updateInformationRequest){

        String email = updateInformationRequest.getEmail();
        String information = updateInformationRequest.getInformation();


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException());


        member.updateInformation(information);
    }

    /**
     * nickname 변가
     * @param updateNicknameRequest
     */
    public void updatePhone(MemberUpdateNicknameRequestDto updateNicknameRequest){

        String email = updateNicknameRequest.getEmail();
        String nickname = updateNicknameRequest.getNickname();


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException());


        member.updateNickname(nickname);
    }


    public void setLoginMemberType(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
        session.setAttribute(LOGIN_STATUS, member.getLoginMemberType());
    }

    @Transactional(readOnly = true)
    public void existsByEmailAndPassword(MemberLoginRequestDto memberLoginRequestDto) {
        memberLoginRequestDto.passwordEncryption(passwordEncoder);
        if(!memberRepository.existsByEmailAndPassword(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword())){
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

    @Transactional(readOnly = true)
    public boolean phoneDuplicateCheck(String phone) {
        return memberRepository.existsByPhone(phone);
    }
}
