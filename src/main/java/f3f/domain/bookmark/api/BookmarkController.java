package f3f.domain.bookmark.api;


import f3f.domain.bookmark.application.BookmarkService;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.domain.message.dto.MessageDTO;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/follow")
    public ResultDataResponseDTO sendMessage(@RequestBody BookmarkDTO.BookmarkRequestDto requestDto){

        log.info(requestDto.toString());
        bookmarkService.followRequest(requestDto);

        return ResultDataResponseDTO.empty();
    }
}
