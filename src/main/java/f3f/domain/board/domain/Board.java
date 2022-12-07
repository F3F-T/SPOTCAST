package f3f.domain.board.domain;

import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.model.BoardType;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "board")
    private List<Likes> likesList = new ArrayList<>();

}
