package f3f.domain.comment.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.comment.domain.Comment;
import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long > {


    Optional<Comment> findById(Long id);

    List<Comment> findByAuthor(Member author);
    List<Comment> findByAuthorEmail(String email);
    List<Comment> findByBoard(Board board);
    List<Comment> findCommentsByParentComment (Comment parentComment);

    List<Comment> findByBoardId(Long pid);

    boolean existsById(Long id);
    boolean existsByAuthor(Member author);


}
