package f3f.domain.comment.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.comment.dto.CommentDTO;
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

    private String content;

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

//    public void setDepth(Long depth){
//        this.depth = depth;
//    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void update(CommentDTO.SaveRequest commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
    public void updateParent(Comment parent){
        this.parentComment = parent;
    }

    @Builder
    public Comment(String content, Member author, Board board, Comment parentComment, List<Comment> childComment, Long depth) {
        this.content = content;
        this.author = author;
        this.board = board;
        this.parentComment = parentComment;
        this.childComment = childComment;
        this.depth = depth;
    }
}
