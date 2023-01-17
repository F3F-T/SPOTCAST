package f3f.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookmarkDTO {

    @Getter
    @NoArgsConstructor
    public static class BookmarkRequestDto{
        private Long followerId;
        private Long followingId;

        @Builder
        public BookmarkRequestDto(Long followerId, Long followingId) {
            this.followerId = followerId;
            this.followingId = followingId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BookmarkListResponseDto{
        private Long boockmarkId;
        private Long memberId;
        private String name;
        private String email;

        @Builder

        public BookmarkListResponseDto(Long boockmarkId, Long memberId, String name, String email) {
            this.boockmarkId = boockmarkId;
            this.memberId = memberId;
            this.name = name;
            this.email = email;
        }
    }
}
