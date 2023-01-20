package f3f.domain.bookmark.api;


import f3f.domain.bookmark.application.BookmarkService;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        bookmarkService.followRequest(requestDto,currentMemberId);
        return ResultDataResponseDTO.empty();
    }

    @DeleteMapping(value = "/bookmark")
    public ResultDataResponseDTO deleteBookmark(@RequestBody BookmarkDTO.BookmarkRequestDto requestDto){

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        bookmarkService.followCancel(requestDto,currentMemberId);

        return ResultDataResponseDTO.empty();
    }

    @GetMapping(value = "/bookmark/follower")
    public ResultDataResponseDTO<Slice<BookmarkDTO.BookmarkListResponseDto>> getBookmarkFollowerList(Pageable pageable){

        Long memberId = SecurityUtil.getCurrentMemberId();
        Slice<BookmarkDTO.BookmarkListResponseDto> followerList = bookmarkService.getFollowerList(memberId,pageable);

        return ResultDataResponseDTO.of(followerList);
    }

    @GetMapping(value = "/bookmark/following")
    public ResultDataResponseDTO<Slice<BookmarkDTO.BookmarkListResponseDto>> getBookmarkFollowingList(Pageable pageable){

        Long memberId = SecurityUtil.getCurrentMemberId();
        Slice<BookmarkDTO.BookmarkListResponseDto> followingList = bookmarkService.getFollowingList(memberId,pageable);

        return ResultDataResponseDTO.of(followingList);
    }
}
