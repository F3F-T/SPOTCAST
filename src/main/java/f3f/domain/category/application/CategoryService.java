package f3f.domain.category.application;

import f3f.domain.category.dao.CategoryRepository;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.category.exception.DuplicateCategoryNameException;
import f3f.domain.category.exception.MaxDepthCategoryException;
import f3f.domain.category.exception.NotFoundCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public long saveCategory(CategoryDTO.SaveRequest category) {

        Category requestCategory = null;

        if (category.getParentCategoryName() == null) {
            if (categoryRepository.existsByName(category.getName())) {
                throw new DuplicateCategoryNameException("카테고리 이름은 같을수 없다 이놈아.");
            }

            Category parentCategory = categoryRepository.findByName("ROOT")
                    .orElseGet(() -> Category.builder()
                            .name("ROOT")
                            .depth(0)
                            .build()
                    );
            //루트 카테고리 먼저 저장
            categoryRepository.save(parentCategory);

            //생성해준 루트 카테고리를 부모 카테고리에 SET
            requestCategory = Category.builder()
                    .name(category.getName())
                    .parentCategory(parentCategory)
                    .depth(1)
                    .build();
        } else {

            String parentCategoryName = category.getParentCategoryName();
            //부모카테고리 찾기
            Category parentCategory = categoryRepository.findByName(parentCategoryName)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));

            requestCategory = Category.builder()
                    .name(category.getName())
                    .parentCategory(parentCategory)
                    .depth(parentCategory.getDepth() + 1)
                    .build();

            if (parentCategory.getDepth() + 1 > 3) {
                throw new MaxDepthCategoryException();
            }

            parentCategory.getChild().add(requestCategory);
        }


        return categoryRepository.save(requestCategory).getId();
    }

    @Transactional
    public Category updateCategory(long categoryId, CategoryDTO.SaveRequest requestCategory) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 카테고리 입니다."));

        Category parentCategory = categoryRepository.findByName(requestCategory.getParentCategoryName())
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 부모 카테고리 입니다."));

        if( category.getDepth() !=  requestCategory.getDepth()){
            throw new IllegalArgumentException("뎁스는 못바꾼다 이놈아");
        }
        Category newCategory = Category.builder()
                .name(requestCategory.getName())
                .depth(requestCategory.getDepth())
                .parentCategory(parentCategory)
                .build();
        category.updateCategory(newCategory);

        return newCategory;
    }

    @Transactional
    public long deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 카테고리 입니다."));

        //todo 자식이 남아있는지 체크하고 버려야함

        categoryRepository.deleteById(category.getId());

        return category.getId();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException("존재하지 않는 카테고리 입니다."));
        return category;
    }
}
