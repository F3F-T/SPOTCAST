package f3f.domain.comment.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> childComment = new ArrayList<>();

    private Long depth;

    public void setDepth(Long depth){
        this.depth = depth;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

//    public void update(CommentRequestDto commentRequestDto) {
//        this.comment = commentRequestDto.getComment();
//    }
    public void updateParent(Comment parent){
        this.parentComment = parent;
    }

    @Builder
    public Comment(Long id, String comment, Member author, Board board, Comment parentComment, List<Comment> childComment, Long depth) {
        this.id = id;
        this.comment = comment;
        this.author = author;
        this.board = board;
        this.parentComment = parentComment;
        this.childComment = childComment;
        this.depth = depth;
    }
}
