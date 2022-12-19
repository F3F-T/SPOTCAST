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
import f3f.domain.comment.exception.NotFoundBoardByIdException;
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
    public Long commentSave(CommentDTO.SaveRequest saveRequest) {

        Member Author = userRepository.findByEmail(saveRequest.getAuthor().getEmail()).orElseThrow(NotFoundUserException::new);
        Board board= boardRepository.findById(saveRequest.getBoard().getId()).orElseThrow(NotFoundBoardByIdException::new);

        Comment comment = saveRequest.toEntity();

        board.getComments().add(comment);//게시글 댓글 리스트에 생성된 댓글 추가.
        commentRepository.save(comment);//댓글 저장

        return comment.getId();

    }

}
