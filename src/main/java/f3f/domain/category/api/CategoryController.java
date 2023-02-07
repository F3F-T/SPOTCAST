package f3f.domain.category.api;

import f3f.domain.category.application.CategoryService;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.global.response.ResultDataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "/category")
    public CategoryDTO.SaveRequest saveCategory(@RequestBody CategoryDTO.SaveRequest request){
        categoryService.saveCategory(request);
        return request;
    }

    @PutMapping(value = "/category/{categoryId}")
    public Category updateCategory(@PathVariable long categoryId, @RequestBody CategoryDTO.SaveRequest request){
        return categoryService.updateCategory(categoryId,request);
    }

    @DeleteMapping(value = "/category/{categoryId}")
    public long deleteCategory(@PathVariable long categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping(value = "/category/{categoryId}")
    public ResultDataResponseDTO<CategoryDTO.CategoryInfo> getCategoryInfo(@PathVariable long categoryId){
            return ResultDataResponseDTO.of(categoryService.getCategoryById(categoryId));
    }
}
