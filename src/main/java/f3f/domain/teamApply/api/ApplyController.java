package f3f.domain.teamApply.api;

import f3f.domain.teamApply.application.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

}
