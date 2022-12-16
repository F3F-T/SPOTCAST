package f3f.domain.category.application;

import f3f.domain.category.dao.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

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