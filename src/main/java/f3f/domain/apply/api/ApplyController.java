package f3f.domain.apply.api;

import f3f.domain.apply.application.ApplyService;
import f3f.domain.apply.domain.Apply;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping(value = "/apply")
    public Apply sendApply(@RequestBody Apply apply) {
        applyService.sendApply(apply);
        return apply;
    }

    @DeleteMapping(value = "/apply/{applyId}/{memberId}")
    public Apply cancelApply(@PathVariable long applyId, @PathVariable long memberId) {
        return applyService.cancelApply(applyId,memberId);
    }


    @GetMapping(value = "/apply/{applyId}/{memberId}")
    public Apply getApply(@PathVariable Long applyId,@PathVariable long memberId){
        return applyService.getApply(applyId,memberId);
    }


    @GetMapping(value ="/apply/recruiter/{memberId}")
    public List<Apply> getRecruiterApplyList(@PathVariable long memberId) {
        return applyService.getRecruiterApplyList(memberId);
    }


    @GetMapping(value ="/apply/volunteer/{memberId}")
    public List<Apply> getVolunteerApplyList(@PathVariable long memberId) {
        return applyService.getVolunteerApplyList(memberId);
    }
}
