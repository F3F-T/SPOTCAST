package f3f.domain.board.dto;

import f3f.domain.category.domain.Category;
import f3f.domain.model.BoardType;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class BoardDTO {

    @Getter
    public static class SaveRequest {
        private String title;

        private String content;

        private long viewCount;

        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        private Category category;

        private Long  memberId;

        @Builder
        public SaveRequest(String title, String content, long viewCount, BoardType boardType, Category category, Long memberId) {
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.boardType = boardType;
            this.category = category;
            this.memberId = memberId;
        }
    }

    @Getter
    public static class BoardInfoDTO {

        private String title;

        private String content;

        private long viewCount;

        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        private Category category;

        private Member member;

        @Builder
        public BoardInfoDTO(String title, String content, long viewCount, BoardType boardType, Category category, Member member) {
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.boardType = boardType;
            this.category = category;
            this.member = member;
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCondition{
        private String keyword;
    }

}
