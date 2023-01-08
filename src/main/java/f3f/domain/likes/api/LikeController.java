package f3f.domain.likes.api;

import f3f.domain.likes.application.LikeService;
import f3f.domain.likes.dto.LikeDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    @Autowired
    private final LikeService likeService;

    /*게시글 좋아요 추가*/
    @PostMapping("/board/{boardId}/like")
    public ResponseEntity addLike(@PathVariable Long boardId,@RequestBody LikeDTO.SaveRequest request ){
        return ResponseEntity.ok(likeService.addLike(request));
    }

    /*게시글 좋아요 삭제*/
    @DeleteMapping("/board/{boardId}/unlike")
    public ResponseEntity deleteLike(@PathVariable Long boardId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        likeService.deleteLike(boardId, memberId);
        return ResponseEntity.ok(boardId);
    }


}
