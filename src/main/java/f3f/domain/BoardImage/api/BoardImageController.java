package f3f.domain.BoardImage.api;

import f3f.domain.BoardImage.application.BoardImageService;
import f3f.domain.BoardImage.domain.BoardImage;
import f3f.domain.BoardImage.dto.BoardImageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardImageController {

    private final BoardImageService boardImageService;

    @PostMapping(value = "/board/boardImage/{boardId}")
    public String saveBoardImage(@PathVariable long boardId, @RequestBody List<MultipartFile> imageList) throws IOException {
        return boardImageService.saveBoardImage(boardId,imageList);
    }

    @DeleteMapping(value = "/board/boardImage")
    public BoardImage deleteBoardImage(@RequestBody BoardImage boardImage){
        return boardImageService.deleteBoardImage(boardImage);
    }

    @GetMapping(value = "/board/boardImage/{boardId}")
    public List<BoardImageDTO.BoardImageInfo> getBoardImageList(@PathVariable long boardId){
        return boardImageService.getBoardImageList(boardId);
    }
}
