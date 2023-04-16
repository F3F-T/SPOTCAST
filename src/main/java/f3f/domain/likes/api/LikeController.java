package f3f.domain.likes.api;

import f3f.domain.likes.application.LikeService;
import f3f.domain.likes.dto.LikeDTO;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    @Autowired
    private final LikeService likeService;

    /*게시글 좋아요 추가*/
    @PostMapping("/board/{boardId}/like")
    public ResultDataResponseDTO addLike(@PathVariable Long boardId){
        Long memberId = SecurityUtil.getCurrentMemberId();
        return ResultDataResponseDTO.of(likeService.addLike(boardId,memberId));
    }

    /*게시글 좋아요 삭제*/
    @DeleteMapping("/board/{boardId}/unlike")
    public ResultDataResponseDTO<Long> deleteLike(@PathVariable Long boardId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        likeService.deleteLike(boardId, memberId);
        return ResultDataResponseDTO.of(boardId);
    }

    @GetMapping("/board/{boardId}/likeList")
    public ResultDataResponseDTO<Map<String ,Object>> getLikeList(@PathVariable Long boardId) {
        Map<String ,Object> listListByBoardId = likeService.getListListByBoardId(boardId);
        return ResultDataResponseDTO.of(listListByBoardId);
    }
}
