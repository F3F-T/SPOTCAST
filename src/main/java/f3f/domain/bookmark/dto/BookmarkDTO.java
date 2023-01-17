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

    @Getter
    @NoArgsConstructor
    public static class BookmarkListResponseDto{
        private Long id;
        private String name;
        private String email;

        @Builder
        public BookmarkListResponseDto(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
}
