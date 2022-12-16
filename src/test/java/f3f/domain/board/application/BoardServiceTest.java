package f3f.domain.board.application;

import f3f.domain.board.dao.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @InjectMocks
    BoardService boardService;


    @Test
    @DisplayName("게시글 저장_성공")
    void saveBoard_success()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("게시글 저장_실패")
    void saveBoard_fail()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 수정_성공")
    void updateBoard_success()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 수정_실패")
    void updateBoard_fail()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("게시글 삭제_성공")
    void deleteBoard_success()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 삭제_실패")
    void deleteBoard_fail()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 정보 조회_성공")
    void getBoardInfo_success()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 정보 조회_실패")
    void getBoardInfo_fail()throws Exception{
        //given

        //when

        //then
    }
}