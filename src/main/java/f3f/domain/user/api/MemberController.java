package f3f.domain.user.api;

import f3f.domain.user.application.MemberService;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
    public ResultDataResponseDTO deleteMember(@RequestBody MemberDeleteRequestDto deleteRequest,
                                                              @PathVariable Long memberId) {
        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.deleteMember(deleteRequest,memberId);

        return ResultDataResponseDTO.empty();
    }

    /**
     * 회원 조회
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}")
    public ResultDataResponseDTO<MemberInfoResponseDto> findMemberInfoById(@PathVariable Long memberId) {

        return ResultDataResponseDTO.of(memberService.findMemberInfoByMemberId(memberId));
    }

    /**
     * 내 정보 찾기
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}/myInfo")
    public ResultDataResponseDTO<MemberInfoResponseDto> findMyInfoById(@PathVariable Long memberId) {
        //memberId 검증
        CheckCurrentUser(memberId);


        return ResultDataResponseDTO.of(memberService.findMyInfo(memberId));
    }

    /**
     * 비밀번호 변경 - 로그인 X
     * @param updatePasswordRequest
     * @return
     */
    @PostMapping("/find/password/{email}")
    public ResultDataResponseDTO changePasswordByForgot(@RequestBody MemberUpdateForgotPasswordRequestDto updatePasswordRequest) {

        memberService.updatePasswordByForgot(updatePasswordRequest);

        return ResultDataResponseDTO.empty();
    }

    /**
     * 비밀번호 변경  - 로그인 O
     * @param updatePasswordRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/change/password")
    public ResultDataResponseDTO changePasswordByLogin(@RequestBody MemberUpdateLoginPasswordRequestDto updatePasswordRequest,
            @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updatePassword(updatePasswordRequest,memberId);

        return ResultDataResponseDTO.empty();
    }


    /**
     * infromation 변경
     * @param updateInformationRequest
     * @param memberId
     * @return
     */
    @PostMapping("/{memberId}/change/information")
    public ResultDataResponseDTO updateInformation(@RequestBody MemberUpdateInformationRequestDto updateInformationRequest,
                                               @PathVariable Long memberId) {

        //memberId 검증
        CheckCurrentUser(memberId);

        memberService.updateInformation(updateInformationRequest,memberId);

        return ResultDataResponseDTO.empty();
    }




    /**
     * 유저 정보 일치 확인
     * @param memberId
     */
    private static void CheckCurrentUser(Long memberId) {
        if(memberId != SecurityUtil.getCurrentMemberId()){
            throw new GeneralException(ErrorCode.NOTCURRENT_MEMBER);
        }
    }
}
