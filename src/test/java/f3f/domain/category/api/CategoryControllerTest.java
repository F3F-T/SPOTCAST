package f3f.domain.category.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.domain.category.application.CategoryService;
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
@WebMvcTest(CategoryController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CategoryControllerTest {


    @MockBean
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("카테고리 저장_성공")
    void saveCategory_success()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("카테고리 저장_실패")
    void saveCategory_fail()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 수정_성공")
    void updateCategory_success()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 수정_실패")
    void updateCategory_fail()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("카테고리 삭제_성공")
    void deleteCategory_success()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 삭제_실패")
    void deleteCategory_fail()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 정보 조회_성공")
    void getCategoryInfo_success()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 정보 조회_실패")
    void getCategoryInfo_fail()throws Exception{
        //given

        //when

        //then
    }

}