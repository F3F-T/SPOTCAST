package f3f.domain.user.api;

import f3f.domain.user.application.EmailCertificationService;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dto.MemberDTO;
import f3f.global.response.ResultDataResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static f3f.domain.user.dto.MemberDTO.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class MemberAuthController {

    private final MemberService memberService;

    private final EmailCertificationService emailCertificationService;

    /**
     * 회원가입
     * @param memberRequestDto
     * @return
     */
    @PostMapping("/signup")
    public ResultDataResponseDTO signup(@Valid @RequestBody MemberSaveRequestDto memberRequestDto) {

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
    public ResultDataResponseDTO<MemberLoginServiceResponseDto> login(@RequestBody MemberLoginRequestDto
                                                                              loginRequestDto, HttpServletResponse response, HttpServletRequest request) {

        MemberLoginServiceResponseDto loginResponseDto = memberService.login(loginRequestDto,response,request);

        return ResultDataResponseDTO.of(loginResponseDto);
    }

    /**
     * JWT 토큰 재발급
     * @return
     */
    @PostMapping("/reissue")
    public ResultDataResponseDTO reissue(HttpServletRequest request,HttpServletResponse response) {

        memberService.reissue(request,response);
        return ResultDataResponseDTO.empty();
    }

    /**
     * 로그아웃
     * @param response
     * @return
     */

    @PostMapping("/logout")
    public ResultDataResponseDTO logout(HttpServletResponse response, HttpServletRequest request) throws IOException {
        memberService.logout(response, request);
        log.info("------------logout------------");
        return ResultDataResponseDTO.of("로그아웃이 되었습니다.");
    }
    /**
     * 이메일 인증 번호 전송
     * @param request
     * @return
     */
    @PostMapping("/email-certification/sends")
    public ResultDataResponseDTO sendEmailCertification(@RequestBody EmailCertificationRequest request) throws MessagingException, UnsupportedEncodingException {
        emailCertificationService.sendEmailForCertification(request.getEmail());
        return ResultDataResponseDTO.empty();
    }

    /**
     * 이메일 인증 번호 확인
     * @param request
     * @return
     */
    @PostMapping("/email-certification/confirms")
    public ResultDataResponseDTO confirmEmailCertification(@RequestBody EmailCertificationRequest request){
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
