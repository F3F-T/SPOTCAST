package f3f.domain.category.dto;

import f3f.domain.category.domain.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {

    @Getter
    public static class SaveRequest {

        private Long categoryId;

        private String name;

        private Integer depth;

        private String parentCategoryName;

        private List<Category> child = new ArrayList<>();

        public SaveRequest(Category category) {
            this.categoryId =category.getId();
            this.name = category.getName();
            this.depth = category.getDepth();
            if (category.getParentCategory() == null) {
                this.parentCategoryName = "ROOT";
            } else {
                this.parentCategoryName = category.getParentCategory().getName();
            }
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
