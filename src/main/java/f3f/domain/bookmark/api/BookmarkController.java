package f3f.domain.bookmark.api;


import f3f.domain.bookmark.application.BookmarkService;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 구현 기능
 * 1. 팔로우
 * 2. 팔로우 취소
 * 3. 팔로우 목록 조회
 * 4. 팔로잉 목록 조회
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping(value = "/bookmark")
    public ResultDataResponseDTO saveBookmark(@RequestBody BookmarkDTO.BookmarkRequestDto requestDto){

        bookmarkService.followRequest(requestDto);

        return ResultDataResponseDTO.empty();
    }

    @DeleteMapping(value = "/bookmark")
    public ResultDataResponseDTO deleteBookmark(@RequestBody Long bookmarkId){

        bookmarkService.followCancel(bookmarkId);

        return ResultDataResponseDTO.empty();
    }

    @GetMapping(value = "/bookmark/follower")
    public ResultDataResponseDTO<List<BookmarkDTO.BookmarkListResponseDto>> getBookmarkFollowerList(){

        Long memberId = SecurityUtil.getCurrentMemberId();
        List<BookmarkDTO.BookmarkListResponseDto> followerList = bookmarkService.getFollowerList(memberId);

        return ResultDataResponseDTO.of(followerList);
    }

    @GetMapping(value = "/bookmark/following")
    public ResultDataResponseDTO<List<BookmarkDTO.BookmarkListResponseDto>> getBookmarkFollowingList(){

        Long memberId = SecurityUtil.getCurrentMemberId();
        List<BookmarkDTO.BookmarkListResponseDto> followingList = bookmarkService.getFollowingList(memberId);

        return ResultDataResponseDTO.of(followingList);
    }
}
