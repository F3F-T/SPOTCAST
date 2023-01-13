package f3f.domain.BoardImage.api;

import f3f.domain.BoardImage.application.BoardImageService;
import f3f.domain.BoardImage.domain.BoardImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardImageController {

    private final BoardImageService boardImageService;

    @PostMapping()
    public BoardImage saveBoardImage(@PathVariable long boardId, @RequestBody List<MultipartFile> imageList){
        return null;
    }

    @DeleteMapping
    public BoardImage deleteBoardImage(){
        return null;
    }

    @GetMapping
    public List<BoardImage> getBoardImageList(){
        return null;
    }
}
