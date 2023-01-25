package f3f.domain.board.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> ,SearchBoardRepository{

    boolean existsBoardById(long boardId);

    List<BoardDTO.BoardInfoDTO> findBoardByCategoryId(long categoryId);

}
