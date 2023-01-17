package f3f.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookmarkDTO {

    @Getter
    @NoArgsConstructor
    public static class BookmarkRequestDto{
        private Long follower_id;
        private Long following_id;

        @Builder
        public BookmarkRequestDto(Long follower_id, Long following_id) {
            this.follower_id = follower_id;
            this.following_id = following_id;
        }
    }
}
