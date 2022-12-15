package f3f.domain.board.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.domain.board.application.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(BoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
class BoardControllerTest {

    @MockBean
    private BoardService boardService;

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


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