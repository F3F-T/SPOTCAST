package f3f.domain.scrapBoard.dto;

import lombok.Builder;
import lombok.Getter;

public class ScrapBoardDTO {
    @Getter
    public static class SaveRequest {

        private Long boardId;


        @Builder
        public SaveRequest(Long boardId) {
            this.boardId = boardId;
        }

    }

    @Getter
    public static class DeleteRequest {

        private Long id;


        @Builder
        public DeleteRequest(Long id) {
            this.id = id;
        }

    }
}
