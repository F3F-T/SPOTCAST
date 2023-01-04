package f3f.domain.comment.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //임시) 표시여부 필드
    private Boolean view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    //부모 댓글을 삭제해도 자식댓글은 남아있음
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> childComment = new ArrayList<>();

    private Long depth;



    public void setDepth(Long depth){
        this.depth = depth;
    }

    //== update ==//
    public void updateComment(String content) {
        this.content = content;
    }
    public void updateView(Boolean view) {
        this.view = view;
    }

//    public void update(CommentRequestDto commentRequestDto) {
//        this.comment = commentRequestDto.getComment();
//    }
    public void updateParent(Comment parent){
        this.parentComment = parent;
    }


    //== delete ==//
    private boolean isRemoved= false;

    public void remove() {
        this.isRemoved = true;
    }

    //== 연관관계 편의 메서드 ==//
//    public void confirmWriter(Member author) {
//        this.author = author;
//        author.getCommentList().add(this);
//    }
//
//    public void confirmPost(Board board) {
//        this.board = board;
//        board.getComments().add(this);
//    }
//
//    public void confirmParent(Comment parent){
//        this.parentComment = parent;
//        parent.addChild(this);
//    }
//
//    public void addChild(Comment child){
//        childComment.add(child);
//    }

    @Builder
    public Comment(Long id, String content, Member author, Board board, Comment parentComment, List<Comment> childComment, Long depth) {
        //this.id = id;
        this.content = content;
        this.author = author;
        this.board = board;
        this.parentComment = parentComment;
        this.childComment = childComment;
        this.depth = depth;
        this.isRemoved = false;
    }

    //== 비즈니스 로직 ==//
    public List<Comment> findRemovableList() {

        List<Comment> result = new ArrayList<>();

        Optional.ofNullable(this.parentComment).ifPresentOrElse(

                parentComment ->{//대댓글인 경우 (부모가 존재하는 경우)
                    if( parentComment.isRemoved()&& parentComment.isAllChildRemoved()){
                        result.addAll(parentComment.getChildComment());
                        result.add(parentComment);
                    }
                },

                () -> {//댓글인 경우
                    if (isAllChildRemoved()) {
                        result.add(this);
                        result.addAll(this.getChildComment());
                    }
                }
        );

        return result;
    }


    //모든 자식 댓글이 삭제되었는지 판단
    private boolean isAllChildRemoved() {
        return getChildComment().stream()//https://kim-jong-hyun.tistory.com/110
                .map(Comment::isRemoved)//지워졌는지 여부로 바꾼다
                .filter(isRemove -> !isRemove)//지워졌으면 true, 안지워졌으면 false이다. 따라서 filter에 걸러지는 것은 false인 녀석들이고, 있다면 false를 없다면 orElse를 통해 true를 반환한다.
                .findAny()//지워지지 않은게 하나라도 있다면 false를 반환
                .orElse(true);//모두 지워졌다면 true를 반환

    }

}
