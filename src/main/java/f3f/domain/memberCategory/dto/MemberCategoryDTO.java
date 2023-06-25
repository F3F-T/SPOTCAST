package f3f.domain.memberCategory.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberCategoryDTO {

    @Getter
    @NoArgsConstructor
    public static class categoryResponseDto{

        private Long id;

        private String name;

        private Integer depth;

        private Long parent_Id;


        @Builder
        public categoryResponseDto(Long id, String name, Integer depth, Long parent_Id) {
            this.id = id;
            this.name = name;
            this.depth = depth;
            this.parent_Id = parent_Id;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class CategoryMyInfo {

        private Long categoryId;

        private String name;

        private Boolean exist;

        @Builder
        public CategoryMyInfo(Long categoryId, String name) {
            this.categoryId = categoryId;
            this.name = name;
            this.exist = false;
        }

        public void updateExist(){
            this.exist = true;
        }
    }
}
