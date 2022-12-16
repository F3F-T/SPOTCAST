package f3f.domain.user.api;

import f3f.domain.user.application.MemberService;
import f3f.domain.user.exception.UnauthenticatedMemberException;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static f3f.domain.user.dto.MemberDTO.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    /**
     * 회원 탈퇴
     * @param deleteRequest
     * @param memberId
     * @return
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@RequestBody MemberDeleteRequestDto deleteRequest,
                                                              @PathVariable Long memberId) {
        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.deleteMember(deleteRequest,memberId);

        return ResponseEntity.ok().build();
    }

    /**
     * 회원 조회
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponseDto> findMemberInfoById(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findMemberInfoByMemberId(memberId));
    }

    /**
     * 내 정보 찾기
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}/myInfo")
    public ResponseEntity<MemberInfoResponseDto> findMyInfoById(@PathVariable Long memberId) {
        //memberId 검증
        CheckCurrentUser(memberId);

        return ResponseEntity.ok(memberService.findMyInfo(memberId));
    }

    /**
     * 비밀번호 변경 - 로그인 X
     * @param updatePasswordRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/find/password")
    public ResponseEntity<Void> changePasswordLoginByForgot(@RequestBody MemberUpdatePasswordRequestDto updatePasswordRequest,
                                                            @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updatePasswordByForgot(updatePasswordRequest);

        return ResponseEntity.ok().build();
    }

    /**
     * 비밀번호 변경  - 로그인 O
     * @param updatePasswordRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/change/password")
    public ResponseEntity<Void> changePasswordLogin(@RequestBody MemberUpdatePasswordRequestDto updatePasswordRequest,
            @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updatePassword(updatePasswordRequest);

        return ResponseEntity.ok().build();
    }

    /**
     * 닉네임 변경
     * @param updateNicknameRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/change/nickname")
    public ResponseEntity<Void> updateNickname(@RequestBody MemberUpdateNicknameRequestDto updateNicknameRequest,
                                                            @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updateNickname(updateNicknameRequest,memberId);

        return ResponseEntity.ok().build();
    }

    /**
     * infromation 변경
     * @param updateInformationRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/change/information")
    public ResponseEntity<Void> updateInformation(@RequestBody MemberUpdateInformationRequestDto updateInformationRequest,
                                               @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updateInformation(updateInformationRequest,memberId);

        return ResponseEntity.ok().build();
    }

    /**
     * 휴대전화 번호 변경
     * @param updatePhoneRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/change/phone")
    public ResponseEntity<Void> updatePhone(@RequestBody MemberUpdatePhoneRequestDto updatePhoneRequest,
                                                  @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updatePhone(updatePhoneRequest,memberId);

        return ResponseEntity.ok().build();
    }

    /**
     * 이메일 중복 검사
     * @param email
     * @return
     */
    @GetMapping("/user-emails/{email}/exists")
    public ResponseEntity<Boolean> duplicateCheckEmail(@PathVariable String email) {

        return ResponseEntity.ok(memberService.emailDuplicateCheck(email));
    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return
     */
    @GetMapping("/user-nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> duplicateCheckNickname(@PathVariable String nickname) {

        return ResponseEntity.ok(memberService.nicknameDuplicateCheck(nickname));
    }

    /**
     * 휴대전화 번호 중복 검사
     * @param phone
     * @return
     */
    @GetMapping("/user-phones/{phone}/exists")
    public ResponseEntity<Boolean> duplicateCheckPhone(@PathVariable String phone) {

        return ResponseEntity.ok(memberService.phoneDuplicateCheck(phone));
    }


    /**
     * 유저 정보 일치 확인
     * @param memberId
     */
    private static void CheckCurrentUser(Long memberId) {
        if(memberId != SecurityUtil.getCurrentMemberId()){
            throw new UnauthenticatedMemberException("유저 정보가 일치하지 않습니다.");
        }
    }
}
