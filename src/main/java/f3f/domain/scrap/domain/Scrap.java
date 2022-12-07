package f3f.domain.scrap.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "scrap")
    private List<Board> boardList = new ArrayList<>();
}
