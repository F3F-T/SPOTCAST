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
import f3f.domain.comment.dto.CommentResponseDto;
import f3f.domain.comment.exception.MaxDepthException;
import f3f.domain.comment.exception.NotFoundBoardByIdException;
import f3f.domain.comment.exception.NotFoundParentException;
import f3f.domain.comment.exception.NotFoundUserException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final MemberRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /*CREATE*/
    @Transactional
    public Long saveComment(Long boardId, CommentDTO.SaveRequest saveRequest) { //TODO 파라미터에 ( HttpServletRequest request ) 동혁이뭐시기

        Comment parent = null;

        Member author = userRepository.findById(saveRequest.getAuthor().getId()).orElseThrow(NotFoundUserException::new);
        Board board = boardRepository.findById(saveRequest.getBoard().getId()).orElseThrow(NotFoundBoardByIdException::new);

        if (board.getId() != boardId){
            throw new IllegalArgumentException();
        }
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
    public List<CommentResponseDto> findCommentsByBoardId(Long boardId){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardByIdException());
        List<Comment> commentList = commentRepository.findByBoardId(board.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }
        return commentResponseDtoList;

//         //태희
//        if (boardRepository.existsById(board.getId())) {
//            return board.getComments();
//        }
//        throw new NotFoundBoardByIdException();
    }

    /* UPDATE */
    @Transactional
    public void updateComment(Long id, CommentDTO.SaveRequest dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.updateComment(dto.getContent());


        //save해줘야함
    }

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
    /* DELETE */
    @Transactional
    public void deleteComment(Long id) {
        //삭제할 코멘트를 찾아옴
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        comment.updateView(false);
//        if(comment.getChildComment().size() == 0 || comment.getChildComment() == null){
//            //OR 표시안함
//            //표시여부를 바꾸거나 삭제하거나
//
//            //CASE 1 : 댓글을 삭제할 경우( 자식 없어야함)
//            commentRepository.deleteById(comment.getId());
//
//            // CASE 2 : 표시여부를 바꾸는 경우
//            comment.updateView(false);
//
//        }else{
//            //자식이  있어서 삭제를 할 수 없다.
//
//            //자식 삭제
//            for (Comment childComment : comment.getChildComment()) {
//                commentRepository.deleteById(childComment.getId());
//            }
//
//            //자식이 다 삭제되면 부모 삭제
//            commentRepository.deleteById(comment.getId());
//        }

//        comment.remove();
//        List<Comment> removableCommentList = comment.findRemovableList();
//        removableCommentList.forEach(removableComment -> commentRepository.delete(removableComment));
//        // 로직 -> Comment에
//        commentRepository.delete(comment);
    }


}
