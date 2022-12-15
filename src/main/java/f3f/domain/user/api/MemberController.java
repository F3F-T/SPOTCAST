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

import static f3f.domain.user.dto.MemberDTO.*;
import static f3f.domain.user.dto.TokenDTO.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberSaveRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.saveMember(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody MemberLoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(memberService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDto) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }
}
