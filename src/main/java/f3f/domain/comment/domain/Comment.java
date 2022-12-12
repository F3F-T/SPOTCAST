package f3f.domain.comment.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.user.domain.User;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content; //댓글내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; //게시글

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> childComment;

    private Long depth;

    public void setDepth(Long depth){
        this.depth = depth;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    @Builder
    public Comment( String content, User author, Board board, Comment parentComment, List<Comment> childComment, Long depth){
        this.content = content;
        this.author = author;
        this.board = board;
        this.parentComment = parentComment;
        this.childComment = childComment;
        this.depth = depth;
    }




}

