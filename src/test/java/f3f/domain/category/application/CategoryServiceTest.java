package f3f.domain.category.application;

import f3f.domain.category.dao.CategoryRepository;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.category.exception.DuplicateCategoryNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    private CategoryDTO.SaveRequest createCategory1(){
        CategoryDTO.SaveRequest  request = CategoryDTO.SaveRequest.builder()
                .name("category1")
                .build();
        return request;
    }

    private CategoryDTO.SaveRequest createCategory2(){
        CategoryDTO.SaveRequest  request = CategoryDTO.SaveRequest.builder()
                .name("category3")
                .depth(1)
                .parentCategoryName("ROOT")
                .build();
        return request;
    }
    @Test
    @DisplayName("카테고리 저장_성공")
    void saveCategory_success()throws Exception{
        //given
        long categoryId = categoryService.saveCategory(createCategory1());
        //when
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        //then
        Assertions.assertThat(category.getId()).isEqualTo(categoryId);
    }


    @Test
    @DisplayName("카테고리 저장_실패_이름 중복 ")
    void saveCategory_fail()throws Exception{
        assertThrows(DuplicateCategoryNameException.class ,
                ()-> categoryService.saveCategory(createCategory1()));
    }

    @Test
    @DisplayName("카테고리 저장_실패_관리자X")
    void saveCategory_fail_not_admin()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 수정_성공")
    void updateCategory_success()throws Exception{
        //when
        Category updateCategory = categoryService.updateCategory(2, createCategory2());
        Category category = categoryRepository.findById(Long.valueOf(2)).orElseThrow();
        //then
        Assertions.assertThat(category.getId()).isEqualTo(2);
        Assertions.assertThat(category.getName()).isEqualTo(updateCategory.getName());
    }

    @Test
    @DisplayName("카테고리 수정_실패")
    void updateCategory_fail()throws Exception{
        //given

        //when

        //then
    }
    @Test
    @DisplayName("카테고리 수정_실패_관리자X")
    void updateCategory_fail_not_admin()throws Exception{
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
    @DisplayName("카테고리 삭제_실패_관리자X")
    void deleteCategory_fail_not_admin()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("카테고리 삭제_실패_자식카테고리 존재")
    void deleteCategory_fail_not_delete_childCategory()throws Exception{
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