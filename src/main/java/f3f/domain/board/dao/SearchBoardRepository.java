package f3f.domain.board.dao;

import f3f.domain.board.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchBoardRepository {
    List<BoardDTO.BoardInfoDTO> getBoardListByCategoryId(long categoryId);
    List<BoardDTO.BoardInfoDTO> getBoardListByUserId(long memberId);
    Page<BoardDTO.BoardInfoDTO> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable);
}
