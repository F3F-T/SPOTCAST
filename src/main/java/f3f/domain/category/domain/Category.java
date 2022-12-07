package f3f.domain.category.domain;

import f3f.domain.board.domain.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
}
