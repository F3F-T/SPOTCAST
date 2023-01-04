package f3f.domain.board.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.category.application.CategoryService;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.publicModel.BoardType;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.user.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    private Category createCategory(){
        return Category.builder()
                .name("카테고리 1")
                .build();
    }


    private Member createMember(){
        return Member.builder()
                .name("ryu")
                .email("rdj1014@naver.com")
                .information("내다")
                .name("닉닉닉")
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .build();
    }

    private BoardDTO.SaveRequest createBoard(){
        return BoardDTO.SaveRequest.builder()
                .title("title1")
                .content("content1")
                .boardType(BoardType.GENERAL)
                .category(createCategory())
                .member(createMember())
                .build();
    }

    @Test
    @DisplayName("게시글 저장_성공")
    void saveBoard_success()throws Exception{
        //given
        BoardDTO.SaveRequest request = createBoard();
        CategoryDTO.SaveRequest categoryRequest= new CategoryDTO.SaveRequest(createCategory());
        //when
        categoryService.saveCategory(categoryRequest);
        Long boardId = boardService.saveBoard(request);
        Board board = boardRepository.findById(boardId).get();

        //then
        assertThat(board.getTitle()).isEqualTo(request.getTitle());
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