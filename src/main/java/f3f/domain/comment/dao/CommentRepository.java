package f3f.domain.comment.dao;

import f3f.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long > {


    Optional<Comment> findById(Long id);
    boolean existsById(Long id);
    List<Comment> findByBoardId(Long boardId);
}
