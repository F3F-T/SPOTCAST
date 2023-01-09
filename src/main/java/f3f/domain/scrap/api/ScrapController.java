package f3f.domain.scrap.api;

import f3f.domain.scrap.application.ScrapService;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.global.response.ResultDataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 필요 기능들
 * 1. 스크랩 박스 리스트 조회
 * 2. 스크랩 박스 삭제
 * 3. 스크랩 박스 생성
 * 4. 스크랩 박스 이름 수정
 */

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    /**
     * 스크랩 박스 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}/scrap")
    public ResultDataResponseDTO<List<ScrapDTO.ScrapInfoDTO>> getScrapBoxList(@PathVariable Long memberId) {

        List<ScrapDTO.ScrapInfoDTO> scrapBoxList = scrapService.getScrapBoxList(memberId);

        return ResultDataResponseDTO.of(scrapBoxList);
    }

    /**
     * 스크랩 박스 삭제
     * @param memberId
     * @return
     */
    @DeleteMapping("/{memberId}/scrap")
    public ResultDataResponseDTO deleteScrapBox(@RequestBody ScrapDTO.ScrapDeleteRequestDTO deleteRequest,@PathVariable Long memberId) {
        scrapService.deleteScrapBox(deleteRequest,memberId);

        return ResultDataResponseDTO.empty();
    }

    /**
     *
     * @param memberId
     * @param saveRequest
     * @return
     */
    @PostMapping("/{memberId}/scrap")
    public ResultDataResponseDTO saveScrapBox(@RequestBody ScrapDTO.SaveRequest saveRequest,@PathVariable Long memberId) {

        scrapService.saveScrapBox(saveRequest,memberId);
        return ResultDataResponseDTO.empty();
    }

    @PatchMapping("/{memberId}/scrap")
    public ResultDataResponseDTO updateScrapBox(@RequestBody ScrapDTO.UpdateRequest updateRequest,@PathVariable Long memberId) {

        scrapService.updateScrapBox(updateRequest,memberId);
        return ResultDataResponseDTO.empty();
    }
}
