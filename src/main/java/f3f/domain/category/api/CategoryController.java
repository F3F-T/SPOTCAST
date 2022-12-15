package f3f.domain.category.api;

import f3f.domain.category.application.CategoryService;
import f3f.domain.category.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "/category")
    public String saveCategory(@RequestBody Category request){
        return null;
    }

    @PutMapping(value = "/category/{categoryId]")
    public Category updateCategory(@PathVariable long categoryId, @RequestBody Category request){
        return null;
    }

    @DeleteMapping(value = "/category/{categoryId}")
    public long deleteCategory(@PathVariable long categoryId){
        return 0;
    }

    @GetMapping(value = "/category/{categoryId}}")
    public  Category getCategoryInfo(@PathVariable long categoryId){
    return null;
    }
}
