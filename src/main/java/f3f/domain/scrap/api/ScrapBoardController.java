package f3f.domain.scrap.api;

import f3f.domain.board.dto.BoardDTO;

import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrapBoard.application.ScrapBoardService;
import f3f.domain.scrapBoard.dto.ScrapBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




/**
 * 필요 기능들
 * 1. 스크랩 목록 조회
 * 2. 스크랩 추가
 * 3. 스크랩 삭제
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class ScrapBoardController {

    private final ScrapBoardService scrapBoardService;

    /**
     * 스크랩 목록 조회
     * @param memberId
     * @param scrapId
     * @return
     */
    @GetMapping("/{memberId}/scrap/{scrapId}")
    public ResponseEntity<List<BoardDTO.BoardInfoDTO>> getScrapList(@PathVariable Long memberId,@PathVariable Long scrapId) {

        List<BoardDTO.BoardInfoDTO> scrapList = scrapBoardService.getScrapList(scrapId, memberId);
        return ResponseEntity.ok(scrapList);
    }

    /**
     * 스크랩 추가
     * @param saveRequest
     * @param memberId
     * @param scrapId
     * @return
     */
    @PostMapping("/{memberId}/scrap/{scrapId}")
    public ResponseEntity<Void> saveScrap(@RequestBody ScrapBoardDTO.SaveRequest saveRequest , @PathVariable Long memberId, @PathVariable Long scrapId) {
        scrapBoardService.saveScrap(memberId,scrapId,saveRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 스크랩 삭제
     * @param deleteRequest
     * @param memberId
     * @param scrapId
     * @return
     */
    @DeleteMapping("/{memberId}/scrap/{scrapId}")
    public ResponseEntity<Void> deleteScrap(@RequestBody ScrapBoardDTO.DeleteRequest deleteRequest , @PathVariable Long memberId, @PathVariable Long scrapId) {
        scrapBoardService.deleteScrap(memberId,scrapId,deleteRequest);
        return ResponseEntity.ok().build();
    }
}