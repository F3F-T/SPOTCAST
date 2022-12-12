package f3f.domain.board.dao;

import f3f.domain.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    boolean existsBoardById(long boardId);

    List<Board> findByCatrgoryId(long categoryId);
}
