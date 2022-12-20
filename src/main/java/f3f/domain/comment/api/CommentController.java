package f3f.domain.comment.api;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.comment.application.CommentService;
import f3f.domain.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;


    /* CREATE */
    @PostMapping("/board/{board_id}/user_id}/comment")
    public ResponseEntity commentSave( @RequestBody CommentDTO.SaveRequest dto) {
        return ResponseEntity.ok(commentService.commentSave(dto));
    }

    /* UPDATE */
    @PatchMapping({"/board/comment/{comment_id}"})
    public ResponseEntity commentUpdate(@PathVariable Long id, @RequestBody CommentDTO.SaveRequest  dto) {
        commentService.commentUpdate(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/board/comment/{comment_id}")
    public ResponseEntity commentDelete(@PathVariable Long id) {
        commentService.commentDelete(id);
        return ResponseEntity.ok(id);
    }



}
