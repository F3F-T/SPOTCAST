package f3f.domain.user.api;

import f3f.domain.user.application.MemberService;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TokenDTO.TokenResponseDTO> login(@RequestBody MemberDTO.MemberLoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(memberService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO.TokenResponseDTO> reissue(@RequestBody TokenDTO.TokenRequestDTO tokenRequestDto) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }
}
