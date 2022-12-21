package f3f.domain.comment.api;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.comment.application.CommentService;
import f3f.domain.comment.domain.Comment;
import f3f.domain.comment.dto.CommentDTO;
import f3f.domain.comment.exception.NotFoundBoardByIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity saveComment( @RequestBody CommentDTO.SaveRequest dto) {
        return ResponseEntity.ok(commentService.saveComment(dto));
    }


    /* UPDATE */
    @PatchMapping({"/board/comment/{comment_id}"})
    public ResponseEntity updateComment(@PathVariable Long comment_id, @RequestBody CommentDTO.SaveRequest  dto) {
        commentService.updateComment(comment_id, dto);
        return ResponseEntity.ok(comment_id);
    }

    /* DELETE */
    @DeleteMapping("/board/comment/{comment_id}")
    public ResponseEntity deleteComment(@PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);
        return ResponseEntity.ok(comment_id);
    }



}
