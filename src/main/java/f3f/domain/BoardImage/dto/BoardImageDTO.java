package f3f.domain.BoardImage.dto;

import lombok.Builder;
import lombok.Getter;

public class BoardImageDTO{

    @Getter
    public static class BoardImageInfo{

        private Long id;
        private String s3Url;

        private Long boardId;

        @Builder
        public BoardImageInfo(Long id, String s3Url, Long boardId) {
            this.id = id;
            this.s3Url = s3Url;
            this.boardId = boardId;
        }
    }
}
