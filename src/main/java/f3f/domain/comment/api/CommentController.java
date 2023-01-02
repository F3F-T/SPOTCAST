package f3f.domain.comment.api;

import f3f.domain.comment.application.CommentService;
import f3f.domain.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;



    /* CREATE */
    @PostMapping("/board/{boardId}/comment")
    public ResponseEntity saveComment(@PathVariable Long boardId, @RequestBody CommentDTO.SaveRequest request) {
        return ResponseEntity.ok(commentService.saveComment(boardId,request));
    }


    /* UPDATE */
    @PatchMapping({"/board/comment/{commentId}"})
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestBody CommentDTO.SaveRequest  dto) {
        commentService.updateComment(commentId, dto);
        return ResponseEntity.ok(commentId);
    }

    /* DELETE */
    @DeleteMapping("/board/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(commentId);
    }



}
