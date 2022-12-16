package f3f.domain.board.dto;

import f3f.domain.board.domain.Board;
import f3f.domain.category.domain.Category;
import f3f.domain.model.BoardType;
import f3f.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class BoardDTO {

    @Getter
    public static class SaveRequest{
        private String title;

        private String content;

        private long viewCount;

        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        private Category category;

        private User user;

        @Builder
        public SaveRequest(String title, String content, long viewCount, BoardType boardType, Category category, User user) {
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.boardType = boardType;
            this.category = category;
            this.user = user;
        }

        public Board toEntity(){
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .viewCount(this.viewCount)
                    .boardType(this.boardType)
                    .category(this.category)
                    .user(this.user)
                    .build();
        }
    }

    @Getter
    public class BoardInfoDTO {

        private String title;

        private String content;

        private long viewCount;

        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        private Category category;

        private User user;

        @Builder
        public BoardInfoDTO(String title, String content, long viewCount, BoardType boardType, Category category, User user) {
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.boardType = boardType;
            this.category = category;
            this.user = user;
        }
    }
}
