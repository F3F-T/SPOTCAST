package f3f.domain.category.dto;

import f3f.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {

    @Getter
    @Setter
    public static class SaveRequest {

        private Long categoryId;

        private String name;

        private Integer depth;

        private String parentCategoryName;

        private List<Category> child = new ArrayList<>();

        @Builder
        public SaveRequest(Long categoryId, String name, Integer depth, String parentCategoryName, List<Category> child) {
            this.categoryId = categoryId;
            this.name = name;
            this.depth = depth;
            this.parentCategoryName = parentCategoryName;
            this.child = child;
        }

        @Builder
        public SaveRequest(Category category) {
            this.categoryId =category.getId();
            this.name = category.getName();
            this.depth = category.getDepth();
            this.parentCategoryName = category.getParentCategory().getName();
            this.child = category.getChild();
        }

        public Category toEntity(){
            return Category.builder()
                    .name(name)
                    .depth(depth)
                    .build();
        }
    }


}
