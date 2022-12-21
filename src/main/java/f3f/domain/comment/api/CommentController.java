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
    @PostMapping("/board/{boardId}/comment")
    public ResponseEntity saveComment(@PathVariable Long boardId, @RequestBody CommentDTO.SaveRequest dto) {
        return ResponseEntity.ok(commentService.saveComment(dto));
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
