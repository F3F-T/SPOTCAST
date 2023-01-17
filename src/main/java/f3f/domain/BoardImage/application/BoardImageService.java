package f3f.domain.BoardImage.application;

import f3f.domain.BoardImage.dao.BoardImageRepository;
import f3f.domain.BoardImage.domain.BoardImage;
import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.infra.aws.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardImageService {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public String  saveBoardImage(long boardId , List<MultipartFile> boardImageList) throws IOException {
        Board board = boardRepository.findById(boardId).orElseThrow();
        for (MultipartFile multipartFile : boardImageList) {
            String imagePath = s3Uploader.upload(multipartFile, "임시 ");
            BoardImage boardImage = BoardImage.builder()
                    .board(board)
                    .s3Url(imagePath)
                    .build();
            boardImageRepository.save(boardImage);
        }
        //1차로 boardId 로 게시글을 찾아온다.
        //찾아왔으면 보드 객체를 이미지에다가 넣어주고
        //이미지 객체 S3랑 DB 에 업데이트 DB 에 넣는값은 S3경로
        return "임시";
    }

    @Transactional
    public BoardImage deleteBoardImage(BoardImage boardImage){
        return null;
    }

    @Transactional(readOnly = true)
    public List<BoardImage> getBoardImageList(long boardId){
        return null;
    }
}
