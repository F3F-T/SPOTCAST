package f3f.domain.user.api;

import f3f.domain.user.application.MemberService;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static f3f.global.constants.MemberConstants.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberDTO.MemberSaveRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.saveMember(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO.TokenResponseDTO> login(@RequestBody MemberDTO.MemberLoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(memberService.login(loginRequestDto, response));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO.TokenResponseDTO> reissue(@RequestBody TokenDTO.TokenRequestDTO tokenRequestDto,HttpServletResponse response, @CookieValue(name = REFRESH_TOKEN) String refreshToken) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto,response, refreshToken));
    }
}
