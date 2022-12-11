package f3f.domain.category.domain;

import f3f.domain.board.domain.Board;
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

    private Long depth;

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    public Category(Long id, String name, Long depth, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.depth = depth;
        this.parentCategory = parentCategory;
    }

    public void updateCategory(Category category){
        this.name = category.getName();
        this.depth = category.getDepth();
        this.parentCategory = category.getParentCategory();

    }
}
