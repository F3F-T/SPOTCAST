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
    public static class BookmarkCancelRequestDto{
        private Long bookmarkId;
        private Long memberId;

        @Builder
        public BookmarkCancelRequestDto(Long bookmarkId, Long memberId) {
            this.bookmarkId = bookmarkId;
            this.memberId = memberId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BookmarkListResponseDto{
        private Long bookmarkId;
        private Long memberId;
        private String name;
        private String email;

        private String profile;

        private Boolean exist;
        @Builder
        public BookmarkListResponseDto(Long bookmarkId, Long memberId, String name, String email, String profile) {
            this.bookmarkId = bookmarkId;
            this.memberId = memberId;
            this.name = name;
            this.email = email;
            this.profile = profile;
        }
        @Builder
        public BookmarkListResponseDto(Long bookmarkId, Long memberId, String name, String email, String profile, Boolean exist) {
            this.bookmarkId = bookmarkId;
            this.memberId = memberId;
            this.name = name;
            this.email = email;
            this.profile = profile;
            this.exist = exist;
        }
    }
}
