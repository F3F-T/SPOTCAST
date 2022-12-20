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
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final MemberRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long commentSave(CommentDTO.SaveRequest saveRequest) { //TODO 파라미터에 ( HttpServletRequest request ) 동혁이뭐시기


        Comment parent = null;

        Member author = userRepository.findById(saveRequest.getAuthor().getId()).orElseThrow(NotFoundUserException::new);
        Board board = boardRepository.findById(saveRequest.getBoard().getId()).orElseThrow(NotFoundBoardByIdException::new);

        //댓글생성
        Comment comment = Comment.builder()
                .author(author)
                .board(board)
                .comment(saveRequest.getComment())
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
            parent.getChildComment().add(comment); //TODO 해줘야되나 ?
            comment.setDepth(parent.getDepth()+1);
        }

        commentRepository.save(comment);//댓글 저장

        return comment.getId();

    }

    /* UPDATE */
    @Transactional
    public void commentUpdate(Long id, CommentDTO.SaveRequest dto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.updateComment(dto.getComment());
    }

    /* DELETE */
    @Transactional
    public void commentDelete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }


}
