package f3f.domain.board.dao;

import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.SearchCondition;
import f3f.domain.publicModel.BoardType;
import f3f.domain.publicModel.SortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchBoardRepository {
    List<BoardInfoDTO> getBoardListByCategoryId(long categoryId, BoardType boardType, SortType sortType);
    List<BoardInfoDTO> getBoardListByUserId(long memberId, BoardType boardType, SortType sortType);
    Page<BoardInfoDTO> findAllBySearchCondition(SearchCondition condition, Pageable pageable);
}
