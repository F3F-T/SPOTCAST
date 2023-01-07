package f3f.domain.board.dto;

import f3f.domain.board.domain.Board;
import f3f.domain.category.domain.Category;
import f3f.domain.publicModel.BoardType;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
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

        private Member member;

        @Builder
        public SaveRequest(String title, String content, long viewCount, BoardType boardType, Category category, Member member) {
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.boardType = boardType;
            this.category = category;
            this.member = member;
        }

        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .viewCount(this.viewCount)
                    .boardType(this.boardType)
                    .category(this.category)
                    .member(this.member)
                    .build();
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

        private MemberDTO.MemberBoardInfoResponseDto member;

        @Builder
        public BoardInfoDTO(String title, String content, long viewCount, BoardType boardType, Category category,
                            MemberDTO.MemberBoardInfoResponseDto member) {
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
    public static class SearchCondition {
        private String keyword;
    }
}
