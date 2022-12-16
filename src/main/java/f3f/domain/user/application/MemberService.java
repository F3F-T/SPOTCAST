package f3f.domain.user.application;

import f3f.domain.model.LoginType;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO.MemberSaveRequestDto;
import f3f.domain.user.dto.TokenDTO;
import f3f.domain.user.dto.TokenDTO.TokenResponseDTO;
import f3f.domain.user.exception.*;
import f3f.global.jwt.TokenProvider;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static f3f.domain.user.dto.MemberDTO.*;
import static f3f.global.constants.MemberConstants.*;
import static f3f.global.constants.jwtConstants.REFRESH_TOKEN_COOKIE_EXPIRE_TIME;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {


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

    @Transactional
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

    /**
     * 회원 탈퇴
     * @param deleteRequest
     */

    @Transactional
    public void deleteMember(MemberDeleteRequestDto deleteRequest){
        findMemberByEmail(deleteRequest.getEmail());

        String email = deleteRequest.getEmail();
        String password = deleteRequest.passwordEncryption(passwordEncoder);

        existsByEmailAndPassword(email, password);


        memberRepository.deleteByEmail(email);
    }


    /**
     * 로그인
     * @param loginRequest
     * @param response
     * @return
     */
    @Transactional(readOnly = true)
    public TokenResponseDTO login(MemberLoginRequestDto loginRequest,  HttpServletResponse response){

        // 로그인 정보로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        //비밀번호 체크 loginRequest 를 authenticationToken 형태로 바꿈
        //authenticate 가 실행될 때 UserDetailsService 에 만들어둔 loadUserByUsername 가 호출되어 DB 의 유저 정보와 일치하는지 확인
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //jwt 토큰 생성
        TokenDTO.TokenSaveDTO tokenSaveDTO = tokenProvider.generateTokenDto(authentication);

        TokenResponseDTO tokenResponseDTO = tokenSaveDTO.toEntity();

        //쿠키 저장 http only
        String refreshToken = tokenSaveDTO.getRefreshToken();
        saveRefreshTokenInStorage(refreshToken); // 추후 DB 나 어딘가 저장 예정
        setRefreshTokenInCookie(response, refreshToken); // 리프레시 토큰 쿠키에 저장

        return tokenResponseDTO;

    }


    /**
     * 토큰 재발급
     * @param tokenRequestDto
     * @param response
     * @param cookieRefreshToken
     * @return
     */
    @Transactional
    public TokenResponseDTO reissue(TokenDTO.TokenRequestDTO tokenRequestDto, HttpServletResponse response, String cookieRefreshToken) {


        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(cookieRefreshToken)) {
            throw new InvalidRefreshTokenException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 유저 확인

        memberRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new MemberNotFoundException("로그아웃 된 사용자입니다."));


//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        String refreshToken = (String)session.getAttribute(REFRESH_TOKEN); // 추후에 디비에서 가져올 예정
        if(refreshToken == null){
            throw new UnauthenticatedMemberException("로그아웃 된 사용자입니다.");
        }


        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.equals(cookieRefreshToken)) {
            throw new UnauthenticatedMemberException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDTO.TokenSaveDTO tokenSaveDTO = tokenProvider.generateTokenDto(authentication);
        TokenResponseDTO tokenDto = tokenSaveDTO.toEntity();

        // 6. 저장소 정보 업데이트
        saveRefreshTokenInStorage(tokenSaveDTO.getRefreshToken());// 추후 디비에 저장
        setRefreshTokenInCookie(response,refreshToken); // 쿠키에 refresh 토큰 저장

        // 토큰 발급
        return tokenDto;
    }



    /**
     * 로그아웃
     */
//    @Transactional(readOnly = true)
//    public void logout(){
//        session.removeAttribute(REFRESH_TOKEN);
//    }

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
    public void updatePassword(MemberUpdatePasswordRequestDto updatePasswordRequest){

        updatePasswordRequest.passwordEncryption(passwordEncoder);
        String beforePassword = updatePasswordRequest.getBeforePassword();
        String afterPassword = updatePasswordRequest.getAfterPassword();


        Member member = findMemberByEmail(updatePasswordRequest.getEmail());

        String email = updatePasswordRequest.getEmail();
        existsByEmailAndPassword(email, beforePassword);

        checkCurrentMember(member);

        checkNotGeneralLoginUser(member);

        member.updatePassword(afterPassword);
    }



    /**
     * 비밀번호 수정 - 로그인되지 않은 경우(본인 인증 이후)
     * @param updatePasswordRequest
     */
    @Transactional
    public void updatePasswordByForgot(MemberUpdatePasswordRequestDto updatePasswordRequest){

        updatePasswordRequest.passwordEncryption(passwordEncoder);

        String email = updatePasswordRequest.getEmail();
        String afterPassword = updatePasswordRequest.getAfterPassword();

        Member member = findMemberByEmail(email);

        checkNotGeneralLoginUser(member);

        member.updatePassword(afterPassword);

    }


    /**
     * 닉네임 수정
     * @param updateNicknameRequest
     */
    @Transactional
    public void updateNickname(MemberUpdateNicknameRequestDto updateNicknameRequest){

        String email = updateNicknameRequest.getEmail();
        String nickname = updateNicknameRequest.getNickname();

        Member member = findMemberByEmail(email);

        if(nicknameDuplicateCheck(nickname)){
            throw new DuplicateNicknameException("중복된 닉네임은 사용할 수 없습니다.");
        }

        checkCurrentMember(member);

        member.updateNickname(nickname);
    }

    /**
     * information 변경
     * @param updateInformationRequest
     */
    @Transactional
    public void updateInformation(MemberUpdateInformationRequestDto updateInformationRequest){

        String email = updateInformationRequest.getEmail();
        String information = updateInformationRequest.getInformation();


        Member member = findMemberByEmail(email);

        checkCurrentMember(member);


        member.updateInformation(information);
    }

    /**
     * phone 변경
     * @param updatePhoneRequest
     */
    @Transactional
    public void updatePhone(MemberUpdatePhoneRequestDto updatePhoneRequest){

        String email = updatePhoneRequest.getEmail();
        String phone = updatePhoneRequest.getPhone();


        Member member = findMemberByEmail(email);

        if(phoneDuplicateCheck(phone)){
            throw new DuplicatePhoneException("중복된 닉네임은 사용할 수 없습니다.");
        }
        checkCurrentMember(member);

        member.updateNickname(phone);
    }

    /**
     * 쿠키에 refresh 토큰 저장
     * @param response
     * @param refreshToken
     */
    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .maxAge(REFRESH_TOKEN_COOKIE_EXPIRE_TIME) //7일
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.setHeader(SET_COOKIE, cookie.toString());
    }

    /**
     * 저장소에 토큰 저장 추후에 DB 나 캐시 고려
     * @param tokenSaveDTO
     */
    private void saveRefreshTokenInStorage(String tokenSaveDTO) {

        session.setAttribute(REFRESH_TOKEN, tokenSaveDTO);
    }


    /**
     * 일반 로그인 유저인지 확인
     * @param member
     */
    private void checkNotGeneralLoginUser(Member member) {
        if(!member.getLoginType().equals(LoginType.GENERAL_LOGIN)){
            throw new NotGeneralLoginType(member.getLoginType().name()+" 로그인으로 회원가입 되어있습니다.");
        }
    }

    /**
     * 현재 로그인된 멤버와 같은 멤버인지 검증
     * @param member
     */
    private void checkCurrentMember(Member member) {
        Member findMember = findMemberByMemberId(SecurityUtil.getCurrentMemberId());

        if(member != findMember){
            throw new UnauthenticatedMemberException("유저 정보가 일치하지 않습니다.");
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
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
    }



    /**
     * email 로 member 찾기
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
    }


    /**
     * email 과 password 로 member 찾기
     * @param email
     * @param password
     */
    @Transactional(readOnly = true)
    public void existsByEmailAndPassword(String email,String password) {
        if(!memberRepository.existsByEmailAndPassword(email, password)){
            throw new MemberNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    /**
     * email 중복 검사
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public boolean emailDuplicateCheck(String email) {
        return memberRepository.existsByEmail(email);
    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return
     */
    @Transactional(readOnly = true)
    public boolean nicknameDuplicateCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * 휴대폰번호 중복 검사
     * @param phone
     * @return
     */
    @Transactional(readOnly = true)
    public boolean phoneDuplicateCheck(String phone) {
        return memberRepository.existsByPhone(phone);
    }
}
