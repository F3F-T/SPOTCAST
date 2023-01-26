package f3f.domain.category.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.memberCategory.domain.MemberCategory;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    private Integer depth;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<MemberCategory> memberCategories = new ArrayList<>();

    @Builder
    public Category(String name, Integer depth, Category parentCategory, List<Category> child) {
        this.name = name;
        this.depth = depth;
        this.parentCategory = parentCategory;
        this.child = child;
    }

    public void updateCategory(Category category) {
        this.name = category.getName();
        this.depth = category.getDepth();
        this.parentCategory = category.getParentCategory();

    }


    public CategoryDTO.CategoryInfo toCategoryInfoDto() {
        return CategoryDTO.CategoryInfo.builder()
                .categoryId(id)
                .name(name)
                .depth(depth)
                .parentCategory(changeParentCategory(parentCategory))
                .child(changeChildCategory(child))
                .build();

    }

    private CategoryDTO.CategoryInfo changeParentCategory(Category parentCategory) {
        CategoryDTO.CategoryInfo categoryInfo;
        if (parentCategory != null) {
            categoryInfo = CategoryDTO.CategoryInfo.builder()
                    .categoryId(parentCategory.getId())
                    .name(parentCategory.getName())
                    .build();
        } else {
            categoryInfo = CategoryDTO.CategoryInfo.builder()
                    .categoryId(0L)
                    .build();
        }

        return categoryInfo;
    }


    private List<CategoryDTO.CategoryInfo> changeChildCategory(List<Category> child) {
        List<CategoryDTO.CategoryInfo> list = child.stream()
                .map(Category::toCategoryInfoDto).collect(Collectors.toList());
        return list;
    }
}
