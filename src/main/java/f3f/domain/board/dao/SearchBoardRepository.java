package f3f.domain.board.dao;

import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchBoardRepository {
    List<BoardInfoDTO> getBoardListByCategoryId(long categoryId);
    List<BoardInfoDTO> getBoardListByUserId(long memberId);
    Page<BoardInfoDTO> findAllBySearchCondition(SearchCondition condition, Pageable pageable);
}
