package f3f.domain.user.model;


import f3f.domain.apply.model.Apply;
import f3f.domain.board.model.Board;
import f3f.domain.comment.model.Comment;
import f3f.domain.likes.model.Likes;
import f3f.domain.model.UserBase;
import f3f.domain.portfolio.model.Portfolio;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User  extends UserBase {

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "author" , fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer" , fetch = FetchType.LAZY)
    private List<Apply> applyList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
