package f3f.domain.user.api;

import f3f.domain.user.application.MemberService;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static f3f.global.constants.MemberConstants.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;

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
    public ResponseEntity<MemberDTO.MemberLoginResponseDto> login(@RequestBody MemberDTO.MemberLoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(memberService.login(loginRequestDto, response));
    }

    /**
     * JWT 토큰 재발급
     * @param tokenRequestDto
     * @param response
     * @param refreshToken
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO.TokenResponseDTO> reissue(@RequestBody TokenDTO.TokenRequestDTO tokenRequestDto,HttpServletResponse response, @CookieValue(name = REFRESH_TOKEN) String refreshToken) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto,response, refreshToken));
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

        memberService.logout(request,response);
        return ResponseEntity.ok().build();
    }




}
