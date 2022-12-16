package f3f.domain.category.application;

import f3f.domain.category.dao.CategoryRepository;
import f3f.domain.category.domain.Category;
import f3f.domain.category.exception.DuplicateCategoryNameException;
import f3f.domain.category.exception.NotFoundCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveCategory(Category category){
        if (categoryRepository.existsByCategoryName(category.getName())){
            throw new DuplicateCategoryNameException("이미 존재하는 카테고리 이름입니다.");
        }

        categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(long categoryId,Category requestCategory){
        Category  category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 카테고리 입니다."));

        category.updateCategory(requestCategory);

        return requestCategory;
    }

    @Transactional
    public long deleteCategory(long categoryId){
        Category  category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 카테고리 입니다."));

        categoryRepository.deleteById(category.getId());

        return category.getId();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(long categoryId){
        Category  category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 카테고리 입니다."));

        return category;
    }
}
