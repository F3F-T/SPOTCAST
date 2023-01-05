package f3f.domain.user.application;

import f3f.domain.publicModel.LoginType;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.dao.RefreshTokenDao;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO.MemberSaveRequestDto;
import f3f.domain.user.dto.TokenDTO;
import f3f.domain.user.exception.*;
import f3f.global.jwt.TokenProvider;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static f3f.domain.user.dto.MemberDTO.*;

@Service
@Slf4j
public class MemberService {


    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private PasswordEncoder passwordEncoder;

    private TokenProvider tokenProvider;
    private MemberRepository memberRepository;
    private RefreshTokenDao refreshTokenDao;

    public MemberService(AuthenticationManagerBuilder authenticationManagerBuilder, @Lazy PasswordEncoder passwordEncoder, TokenProvider tokenProvider, MemberRepository memberRepository, RefreshTokenDao refreshTokenDao) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
        this.refreshTokenDao = refreshTokenDao;
    }

    /**
     * 회원가입
     * @param saveRequest
     * @return
     */

    @Transactional
    public Long saveMember(MemberSaveRequestDto saveRequest){
        if(emailDuplicateCheck(saveRequest.getEmail())){
            throw new GeneralException(ErrorCode.DUPLICATION_EMAIL,"이미 가입되어 있는 이메일입니다.");
        }

        saveRequest.passwordEncryption(passwordEncoder);
        Member member = saveRequest.toEntity();
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 회원 탈퇴
     * @param deleteRequest
     */

    @Transactional
    public void deleteMember(MemberDeleteRequestDto deleteRequest, Long memberId){

        Member findMember = findMemberByMemberId(memberId);

        if(findMember.getEmail() != deleteRequest.getEmail()){

            throw new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST,"아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        String password = deleteRequest.getPassword();
        if(!passwordEncoder.matches(password, findMember.getPassword()))
        {
            throw new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST,"아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        existsByIdAndPassword(memberId, findMember.getPassword());

        memberRepository.deleteById(memberId);
    }


    /**
     * 로그인
     * @param loginRequest
     * @return
     */
    @Transactional(readOnly = true)
    public MemberLoginServiceResponseDto login(MemberLoginRequestDto loginRequest){

        // 로그인 정보로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        //비밀번호 체크 loginRequest 를 authenticationToken 형태로 바꿈
        //authenticate 가 실행될 때 UserDetailsService 에 만들어둔 loadUserByUsername 가 호출되어 DB 의 유저 정보와 일치하는지 확인
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //jwt 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        //response 에 유저 정보를 담기 위한 findById
        Member findMember = memberRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        //유저 정보 + 토큰 값
        MemberLoginServiceResponseDto memberLoginResponse = tokenDto.toLoginEntity(findMember);

        String refreshToken = tokenDto.getRefreshToken();
        saveRefreshTokenInStorage(refreshToken, Long.valueOf(authentication.getName())); // 추후 DB 나 어딘가 저장 예정


        return memberLoginResponse;

    }


    /**
     * 토큰 재발급
     * @param tokenRequestDto
     * @return
     */
    @Transactional
    public TokenDTO reissue(TokenDTO.TokenRequestDTO tokenRequestDto) {


        // 1. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 2. 저장소에서 Member ID 를 기반으로 유저 확인
        if(!memberRepository.existsById(Long.valueOf(authentication.getName()))){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다.");
        }

        // 3. cache 에서 member Id 를 기반으로 refresh token 확인
        String refreshToken = refreshTokenDao.getRefreshToken(Long.valueOf(authentication.getName()));
        if(refreshToken == null){
            throw new GeneralException(ErrorCode.INVALID_REFRESHTOKEN, "로그아웃 된 사용자입니다.");
        }

        // 4. Refresh Token 검증
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new GeneralException(ErrorCode.INVALID_REFRESHTOKEN, "유효하지 않은 refresh token 입니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDTO tokenDTO = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        saveRefreshTokenInStorage(tokenDTO.getRefreshToken(),Long.valueOf(authentication.getName()));// 추후 디비에 저장

        // 토큰 발급
        return tokenDTO;
    }


    /**
     * 로그아웃
     *
     */
    @Transactional
    public void logout(Long memberId){
        refreshTokenDao.removeRefreshToken(memberId);
    }

    /**

    /**
     * 회원 정보 조회
     * @param memberId
     * @return
     */

    @Transactional(readOnly = true)
    public MemberInfoResponseDto findMemberInfoByMemberId(Long memberId){
        return findMemberByMemberId(memberId)
                .toFindMemberDto();
    }

    /**
     * 내 정보 조회
     * @return
     */

    @Transactional(readOnly = true)
    public MemberInfoResponseDto findMyInfo(Long memberId){
        return findMemberByMemberId(memberId)
                .toFindMemberDto();
    }

    /**
     * 비밀번호 수정 - 로그인된 상태인 경우
     * @param updatePasswordRequest
     */
    @Transactional
    public void updatePassword(MemberUpdateLoginPasswordRequestDto updatePasswordRequest, Long memberId){

        String beforePassword = updatePasswordRequest.getBeforePassword();
        String afterPassword = updatePasswordRequest.getAfterPassword();

        Member findMember = findMemberByMemberId(memberId);

        if(!passwordEncoder.matches(beforePassword, findMember.getPassword()))
        {
            throw new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST,"아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        existsByIdAndPassword(memberId,findMember.getPassword());

        checkNotGeneralLoginUser(findMember);

        findMember.updatePassword(afterPassword);
    }



    /**
     * 비밀번호 수정 - 로그인되지 않은 경우(본인 인증 이후)
     * @param updatePasswordRequest
     */
    @Transactional
    public void updatePasswordByForgot(MemberUpdateForgotPasswordRequestDto updatePasswordRequest){

        updatePasswordRequest.passwordEncryption(passwordEncoder);

        String email = updatePasswordRequest.getEmail();
        String afterPassword = updatePasswordRequest.getAfterPassword();

        Member member = findMemberByEmail(email);

        checkNotGeneralLoginUser(member);

        member.updatePassword(afterPassword);

    }



    /**
     * information 변경
     * @param updateInformationRequest
     */
    @Transactional
    public void updateInformation(MemberUpdateInformationRequestDto updateInformationRequest, Long memberId){

        String information = updateInformationRequest.getInformation();

        Member member = findMemberByMemberId(memberId);

        member.updateInformation(information);
    }




    /**
     * redis 에 refresh token 저장
     * @param refreshToken
     * @param memberId
     */
    private void saveRefreshTokenInStorage(String refreshToken, Long memberId) {
        refreshTokenDao.createRefreshToken(memberId,refreshToken);
    }


    /**
     * 일반 로그인 유저인지 확인
     * @param member
     */
    private void checkNotGeneralLoginUser(Member member) {
        if(!member.getLoginType().equals(LoginType.GENERAL_LOGIN)){
             throw new GeneralException(ErrorCode.DUPLICATION_SIGNUP,member.getLoginType().getType()+" 로그인으로 회원가입 되어있습니다.");
        }
    }


    /**
     * memberId로 멤버 찾기
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

    }




    /**
     * email 로 member 찾기
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {

        Member findMember = memberRepository.findByEmail(email);
        if(findMember==null){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다.");
        }

        return findMember;
    }


    /**
     * email 과 password 로 member 찾기
     * @param email
     * @param password
     */
    @Transactional(readOnly = true)
    public void existsByEmailAndPassword(String email,String password) {
        if(!memberRepository.existsByEmailAndPassword(email, password)){
            throw new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST,"아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public void existsByIdAndPassword(Long memberId,String password) {
        if(!memberRepository.existsByIdAndPassword(memberId, password)){
            throw new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST,"아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    /**
     * email 중복 검사
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public boolean emailDuplicateCheck(String email) {
        System.out.println("email = " + email);
        return memberRepository.existsByEmail(email);
    }


}
