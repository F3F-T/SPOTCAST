package f3f.domain.user.api;

import f3f.domain.user.application.EmailCertificationService;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static f3f.global.constants.MemberConstants.REFRESH_TOKEN;
import static f3f.global.constants.MemberConstants.SET_COOKIE;
import static f3f.global.constants.SecurityConstants.JSESSIONID;
import static f3f.global.constants.SecurityConstants.REMEMBER_ME;
import static f3f.global.constants.jwtConstants.REFRESH_TOKEN_COOKIE_EXPIRE_TIME;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;

    private final EmailCertificationService emailCertificationService;
    /**
     * 회원가입
     * @param memberRequestDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberDTO.MemberSaveRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.saveMember(memberRequestDto));
    }

    /**
     * 로그인
     * @param loginRequestDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<MemberDTO.MemberLoginResponseDto> login(@RequestBody MemberDTO.MemberLoginRequestDto
                                                                              loginRequestDto, HttpServletResponse response) {

        MemberDTO.MemberLoginServiceResponseDto loginResponseDto = memberService.login(loginRequestDto);
        String refreshToken = loginResponseDto.getRefreshToken();
        setRefreshTokenInCookie(response, refreshToken); // 리프레시 토큰 쿠키에 저장
        return ResponseEntity.ok(loginResponseDto.toEntity());
    }

    /**
     * JWT 토큰 재발급
     * @param tokenRequestDto
     * @param response
     * @param refreshToken
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO.TokenResponseDTO> reissue(@RequestBody TokenDTO.TokenRequestDTO tokenRequestDto,HttpServletResponse response,
                                                             @CookieValue(value="refreshToken", required = false) String refreshToken) {
        System.out.println("refreshToken = " + refreshToken);
        TokenDTO tokenDTO = memberService.reissue(tokenRequestDto, refreshToken);

        String newRefreshToken = tokenDTO.getRefreshToken();
        setRefreshTokenInCookie(response,newRefreshToken);
        TokenDTO.TokenResponseDTO tokenResponseDTO = tokenDTO.toEntity();

        return ResponseEntity.ok(tokenResponseDTO);
    }

    /**
     * 로그아웃
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.getSession().removeAttribute(REFRESH_TOKEN);
        deleteCookie(response,JSESSIONID);
        deleteCookie(response,REMEMBER_ME);
        deleteCookie(response,REFRESH_TOKEN);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email-certification/sends")
    public ResponseEntity<Void> sendEmailCertification(@RequestBody MemberDTO.EmailCertificationRequest request){
        emailCertificationService.sendEmailForCertification(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email-certification/confirms")
    public ResponseEntity<Void> confirmEmailCertification(@RequestBody MemberDTO.EmailCertificationRequest request){
        emailCertificationService.verifyEmail(request);
        return ResponseEntity.ok().build();
    }
    /**
     * 이메일 중복 검사
     * @param email
     * @return
     */
    @GetMapping("/member-emails/{email}/exists")
    public ResponseEntity<Boolean> duplicateCheckEmail(@PathVariable String email) {

        return ResponseEntity.ok(memberService.emailDuplicateCheck(email));
    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return
     */
    @GetMapping("/member-nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> duplicateCheckNickname(@PathVariable String nickname) {

        return ResponseEntity.ok(memberService.nicknameDuplicateCheck(nickname));
    }

    /**
     * 휴대전화 번호 중복 검사
     * @param phone
     * @return
     */
    @GetMapping("/member-phones/{phone}/exists")
    public ResponseEntity<Boolean> duplicateCheckPhone(@PathVariable String phone) {

        return ResponseEntity.ok(memberService.phoneDuplicateCheck(phone));
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
                .sameSite("None")
                .httpOnly(true)
                .build();

        Cookie cookie2 = new Cookie(REFRESH_TOKEN,refreshToken);
        cookie2.setMaxAge((int) REFRESH_TOKEN_COOKIE_EXPIRE_TIME);
        cookie2.setPath("/");

        response.setHeader(SET_COOKIE, cookie.toString());
    }
    /**
     * 쿠키 제거
     * @param response
     * @param cookieName
     */
    private void deleteCookie(HttpServletResponse response,String cookieName) {
        Cookie cookie = new Cookie(cookieName, null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
        cookie.setMaxAge(0); // 유효시간을 0으로 설정
        response.addCookie(cookie);
    }
}
