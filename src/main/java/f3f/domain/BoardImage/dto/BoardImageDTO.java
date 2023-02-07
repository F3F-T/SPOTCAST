package f3f.domain.BoardImage.dto;

import lombok.Builder;
import lombok.Getter;

public class BoardImageDTO{

    @Getter
    public static class BoardImageInfo{

        private Long id;
        private String s3Url;

        @Builder
        public BoardImageInfo(Long id, String s3Url) {
            this.id = id;
            this.s3Url = s3Url;
        }
    }
}
