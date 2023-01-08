package f3f.domain.likes.dto;

import f3f.domain.board.domain.Board;
import f3f.domain.likes.domain.Likes;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class LikeDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SaveRequest {

        @NotNull
        private Member member;
        @NotNull
        private Board board;

        public Likes toEntity() {
            return Likes.builder()
                    .member(member)
                    .board(board)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteLike{

        private Board board;
        private Long id;
    }
}
