package f3f.domain.comment.application;

/* CRUD
 *
 * 1. CREATE
 * 2. READ
 * 3. UPDATE
 * 4. DELETE
 *
 * */

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.comment.dao.CommentRepository;
import f3f.domain.comment.domain.Comment;
import f3f.domain.comment.dto.CommentDTO;
import f3f.domain.comment.exception.MaxDepthException;
import f3f.domain.comment.exception.NotFoundBoardByIdException;
import f3f.domain.comment.exception.NotFoundParentException;
import f3f.domain.comment.exception.NotFoundUserException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final MemberRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /*CREATE*/
    @Transactional
    public Long saveComment(CommentDTO.SaveRequest saveRequest) { //TODO 파라미터에 ( HttpServletRequest request ) 동혁이뭐시기


        Comment parent = null;

        Member author = userRepository.findById(saveRequest.getAuthor().getId()).orElseThrow(NotFoundUserException::new);
        Board board = boardRepository.findById(saveRequest.getBoard().getId()).orElseThrow(NotFoundBoardByIdException::new);

        //댓글생성
        Comment comment = Comment.builder()
                .author(author)
                .board(board)
                .content(saveRequest.getContent())
                .build();


        //부모댓글 존재하면
        if (saveRequest.getParentComment() != null) {
            parent = commentRepository.findById(saveRequest.getParentComment().getId())
                    .orElseThrow(NotFoundParentException::new);

            //대댓글까지만 허용
            if(parent.getDepth()>=1){
                throw new MaxDepthException();
            }

            comment.updateParent(parent); //부모 update
            //parent.getChildComment().add(comment); //TODO 해줘야되나 ? ->nope
            comment.setDepth(parent.getDepth()+1);
        }

        commentRepository.save(comment);//댓글 저장

        return comment.getId();

    }
    /*READ*/
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByPost(Board board){

        if (boardRepository.existsById(board.getId())) {
            return board.getComments();
        }
        throw new NotFoundBoardByIdException();
    }

    /* UPDATE */
    @Transactional
    public void updateComment(Long comment_id, CommentDTO.SaveRequest dto) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + comment_id));

        comment.updateComment(dto.getContent());
    }

    /* DELETE */
    @Transactional
    public void deleteComment(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + comment_id));

        /*부모 댓글 지울 경우
        1. 대댓글이 남아있는 경우 : 댓글은 내용이 지워지나, DB에와 화면에서는 지워지지 않고, "삭제된 댓글입니다"라고 표시
        2. 대댓글이 아예 존재하지 않는 경우 : 곧바로 DB에서 삭제
        3. 대댓글이 존재하나 모두 삭제된 대댓글인 경우 : 댓글과, 달려있는 대댓글 모두 DB에서 일괄 삭제, 화면상에도 표시되지 않음
        */


        /*자식 댓글 지울 경우
        1. 대댓글의 부모댓글이 남아있는 경우 : 대댓글만 삭제, 그러나 DB에서 완전히 지워지지는 않고, 화면상에는 "삭제된 댓글입니다" 라고만 표시
        2. 대댓글의 부모댓글이 삭제된 댓글인 경우
            2-1.현재 지운 대댓글로 인해, 부모에 달려있는 대댓글이 모두 삭제된 경우 -> 부모를 포함한 모든 대댓글을 DB에서 일괄 삭제, 화면상에서도 지움
            2-2. 다른 대댓글이 아직 삭제되지 않고 남아있는 경우 -> 해당 대댓글만 삭제, 그러나 DB에서 삭제되지는 않고, 화면상에는 "삭제된 댓글입니다"라고 표시


        */

        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        removableCommentList.forEach(removableComment -> commentRepository.delete(removableComment));
        // 로직 -> Comment에

        commentRepository.delete(comment);
    }


}
