package f3f.domain.comment.api;

import f3f.domain.comment.application.CommentService;
import f3f.domain.comment.dto.CommentDTO;
import f3f.domain.comment.dto.CommentResponseDto;
import f3f.global.response.ResultDataResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResultDataResponseDTO saveComment(@PathVariable Long boardId, @RequestBody CommentDTO.SaveRequest request) {
        return ResultDataResponseDTO.of(commentService.saveComment(boardId,request));
    }


    /* UPDATE */
    @PatchMapping({"/board/comment/{commentId}"})
    public ResultDataResponseDTO<Long> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO.SaveRequest  dto) {
        commentService.updateComment(commentId, dto);
        return  ResultDataResponseDTO.of(commentId);
    }

    /* DELETE */
    @DeleteMapping("/board/comment/{commentId}")
    public ResultDataResponseDTO<Long> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResultDataResponseDTO.of(commentId);
    }

    @GetMapping(value = "/board/{boardId}/commentList")
    public ResultDataResponseDTO<List<CommentResponseDto>> getCommentListByBoardId(@PathVariable Long boardId){
        List<CommentResponseDto> commentsByBoardId = commentService.findCommentsByBoardId(boardId);
        return ResultDataResponseDTO.of(commentsByBoardId);
    }
}
