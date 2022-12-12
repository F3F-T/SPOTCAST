package f3f.domain.category.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

//    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
//    private List<Board> boardList = new ArrayList<>();

    @Builder
    public Category(String name, Integer depth,Category parentCategory, List<Category> child) {
        this.name = name;
        this.depth = depth;
        this.parentCategory = parentCategory;
        this.child = child;
    }

    public void updateCategory(Category category){
        this.name = category.getName();
        this.depth = category.getDepth();
        this.parentCategory = category.getParentCategory();

    }
}
