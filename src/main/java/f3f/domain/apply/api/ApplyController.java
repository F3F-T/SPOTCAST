package f3f.domain.apply.api;

import f3f.domain.apply.application.ApplyService;
import f3f.domain.apply.domain.Apply;
import f3f.domain.apply.dto.ApplyDTO;
import f3f.global.response.ResultDataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping(value = "/apply")
    public ResultDataResponseDTO<ApplyDTO.SaveRequest> sendApply(@RequestBody ApplyDTO.SaveRequest request) {
        applyService.sendApply(request);
        return ResultDataResponseDTO.of(request);
    }

    @DeleteMapping(value = "/apply/{applyId}/{memberId}")
    public ResultDataResponseDTO<Apply> cancelApply(@PathVariable long applyId, @PathVariable long memberId) {
        return ResultDataResponseDTO.of(applyService.cancelApply(applyId,memberId));
    }


    @GetMapping(value = "/apply/{applyId}/{memberId}")
    public ResultDataResponseDTO<ApplyDTO.ApplyInfo> getApply(@PathVariable Long applyId, @PathVariable long memberId){
        return ResultDataResponseDTO.of(applyService.getApply(applyId,memberId));
    }


    @GetMapping(value ="/apply/recruiter/{recruiterId}")
    public ResultDataResponseDTO<List<ApplyDTO.ApplyInfo>> getRecruiterApplyList(@PathVariable long recruiterId) {
        return ResultDataResponseDTO.of(applyService.getRecruiterApplyList(recruiterId));
    }


    @GetMapping(value ="/apply/volunteer/{volunteerId}")
    public ResultDataResponseDTO<List<ApplyDTO.ApplyInfo>> getVolunteerApplyList(@PathVariable long volunteerId) {
        return ResultDataResponseDTO.of(applyService.getVolunteerApplyList(volunteerId));
    }
}
