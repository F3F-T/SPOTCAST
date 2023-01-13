package f3f.domain.user.api;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import f3f.domain.user.application.EmailCertificationService;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.CookieUtil;
import f3f.global.util.SecurityUtil;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static f3f.global.constants.JwtConstants.ACCESSTOKEN;
import static f3f.global.constants.JwtConstants.ACCESS_TOKEN_COOKIE_EXPIRE_TIME;
import static f3f.global.constants.SecurityConstants.JSESSIONID;
import static f3f.global.constants.SecurityConstants.REMEMBER_ME;

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
    public ResultDataResponseDTO signup(@Valid @RequestBody MemberDTO.MemberSaveRequestDto memberRequestDto) {

        memberService.saveMember(memberRequestDto);

        return ResultDataResponseDTO.empty();
    }

    /**
     * 로그인
     * @param loginRequestDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultDataResponseDTO<MemberDTO.MemberLoginServiceResponseDto> login(@RequestBody MemberDTO.MemberLoginRequestDto
                                                                              loginRequestDto, HttpServletResponse response) {

        MemberDTO.MemberLoginServiceResponseDto loginResponseDto = memberService.login(loginRequestDto);

        CookieUtil.addCookie(response,ACCESSTOKEN,loginResponseDto.getAccessToken(),  ACCESS_TOKEN_COOKIE_EXPIRE_TIME);

        return ResultDataResponseDTO.of(loginResponseDto);
    }

    /**
     * JWT 토큰 재발급
     * @param tokenRequestDto
     * @return
     */
    @PostMapping("/reissue")
    public ResultDataResponseDTO<TokenDTO.TokenResponseDTO> reissue(@RequestBody TokenDTO.TokenRequestDTO tokenRequestDto) {
        TokenDTO.TokenResponseDTO tokenResponseDTO = memberService.reissue(tokenRequestDto);
        return ResultDataResponseDTO.of(tokenResponseDTO);
    }

    /**
     * 로그아웃
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public ResultDataResponseDTO logout(HttpServletResponse response, HttpServletRequest request) throws IOException {
            memberService.logout(SecurityUtil.getCurrentMemberId(),response, request);


        return ResultDataResponseDTO.empty();
    }
    /**
     * 소셜 로그인 시 정보 return
     * @return
     */
    @GetMapping("/myInfo")
    public ResultDataResponseDTO<MemberDTO.MemberInfoResponseDto> findMyInfoById() {

        return ResultDataResponseDTO.of(memberService.findMyInfo(SecurityUtil.getCurrentMemberId()));
    }
    /**
     * 이메일 인증 번호 전송
     * @param request
     * @return
     */
    @PostMapping("/email-certification/sends")
    public ResultDataResponseDTO sendEmailCertification(@RequestBody MemberDTO.EmailCertificationRequest request) throws MessagingException, UnsupportedEncodingException {
        emailCertificationService.sendEmailForCertification(request.getEmail());
        return ResultDataResponseDTO.empty();
    }

    /**
     * 이메일 인증 번호 확인
     * @param request
     * @return
     */
    @PostMapping("/email-certification/confirms")
    public ResultDataResponseDTO confirmEmailCertification(@RequestBody MemberDTO.EmailCertificationRequest request){
        emailCertificationService.verifyEmail(request);
        return ResultDataResponseDTO.empty();
    }
    /**
     * 이메일 중복 검사
     * @param email
     * @return
     */
    @GetMapping("/member-emails/{email}/exists")
    public ResultDataResponseDTO<Boolean> duplicateCheckEmail(@PathVariable String email) {

        return ResultDataResponseDTO.of(memberService.emailDuplicateCheck(email));
    }


}
