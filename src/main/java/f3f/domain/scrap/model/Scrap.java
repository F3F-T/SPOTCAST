package f3f.domain.scrap.model;

import f3f.domain.board.model.Board;
import f3f.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class
Scrap {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "scrap")
    private List<Board> boardList = new ArrayList<>();
}
