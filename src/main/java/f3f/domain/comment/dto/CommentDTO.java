package f3f.domain.comment.dto;

import f3f.domain.board.domain.Board;
import f3f.domain.comment.domain.Comment;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CommentDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SaveRequest{

        @NotNull
        private String comment;
        @NotNull
        private Member author;
        @NotNull
        private Board board;

        private long parentId;
        private Comment parentComment;
        private List<Comment> childComment;
        private Long depth;

        /*Dto -> Entity*/
        public Comment toEntity(){
            return Comment.builder()
                    .comment(this.comment)
                    .board(this.board)
                    .author(this.author)
                    .parentComment(this.parentComment)
                    .childComment(this.childComment)
                    .depth(this.depth)
                    .build();

        }
    }



}
