package f3f.domain.user.api;

import f3f.domain.user.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static f3f.domain.user.dto.MemberDTO.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponseDto> findMemberInfoById(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findMemberInfoByMemberId(memberId));
    }

}
