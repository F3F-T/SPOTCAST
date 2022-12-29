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
    @DisplayName("게시글 저장_실패_카테고리 X")
    void saveBoard_fail_unknown_category()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("게시글 저장_실패_유저정보 X")
    void saveBoard_fail_unknown_userInfo()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 저장_실패_로그인 X")
    void saveBoard_fail_unknown_login()throws Exception{
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
    @DisplayName("게시글 수정_실패_카테고리 X")
    void updateBoard_fail_unknown_category()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("게시글 수정_실패_유저정보 X")
    void updateBoard_fail_unknown_userInfo()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 수정_실패_로그인 X")
    void updateBoard_fail_unknown_login()throws Exception{
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
    @DisplayName("게시글 삭제_실패_본인 게시글 X")
    void deleteBoard_fail_userMissMatch()throws Exception{
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

    @Test
    @DisplayName("게시글 정보 조회_성공_카테고리식별자로 조회 ")
    void getBoardInfo_success_by_catgoryId()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("게시글 정보 조회_성공_유저식별자로 조회 ")
    void getBoardInfo_success_by_userId()throws Exception{
        //given

        //when

        //then
    }
}