package f3f.domain.apply.api;

import f3f.domain.apply.application.ApplyService;
import f3f.domain.apply.domain.Apply;
import f3f.global.response.ResultDataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping(value = "/apply")
    public ResultDataResponseDTO<Apply> sendApply(@RequestBody Apply apply) {
        applyService.sendApply(apply);
        return ResultDataResponseDTO.of(apply);
    }

    @DeleteMapping(value = "/apply/{applyId}/{memberId}")
    public ResultDataResponseDTO<Apply> cancelApply(@PathVariable long applyId, @PathVariable long memberId) {
        return ResultDataResponseDTO.of(applyService.cancelApply(applyId,memberId));
    }


    @GetMapping(value = "/apply/{applyId}/{memberId}")
    public ResultDataResponseDTO<Apply> getApply(@PathVariable Long applyId,@PathVariable long memberId){
        return ResultDataResponseDTO.of(applyService.getApply(applyId,memberId));
    }


    @GetMapping(value ="/apply/recruiter/{memberId}")
    public ResultDataResponseDTO<List<Apply>> getRecruiterApplyList(@PathVariable long memberId) {
        return ResultDataResponseDTO.of(applyService.getRecruiterApplyList(memberId));
    }


    @GetMapping(value ="/apply/volunteer/{memberId}")
    public ResultDataResponseDTO<List<Apply>> getVolunteerApplyList(@PathVariable long memberId) {
        return ResultDataResponseDTO.of(applyService.getVolunteerApplyList(memberId));
    }
}
