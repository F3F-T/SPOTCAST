package f3f.domain.scrapBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ScrapBoardDTO {
    @Getter
    @NoArgsConstructor
    public static class SaveRequest {

        private Long boardId;


        @Builder
        public SaveRequest(Long boardId) {
            this.boardId = boardId;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class DeleteRequest {

        private Long scrapBoardId;


        @Builder
        public DeleteRequest(Long scrapBoardId) {
            this.scrapBoardId = scrapBoardId;
        }

    }
}
