package f3f.domain.BoardImage.application;

import f3f.domain.BoardImage.dao.BoardImageRepository;
import f3f.domain.BoardImage.domain.BoardImage;
import f3f.domain.board.dao.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardImageService {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;

    @Transactional
    public BoardImage saveBoardImage(long boardId , List<BoardImage> boardImage){
        //1차로 boardId 로 게시글을 찾아온다.
        //찾아왔으면 보드 객체를 이미지에다가 넣어주고
        //이미지 객체 S3랑 DB 에 업데이트 DB 에 넣는값은 S3경로
        return null;
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
