package f3f.domain.board.dao;

import f3f.domain.board.domain.ProfitStatus;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.SearchCondition;
import f3f.domain.publicModel.BoardType;
import f3f.domain.publicModel.SortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchBoardRepository {
    Page<BoardDTO.BoardListResponse> getBoardListInfoByCategoryId(BoardType boardType, Long categoryId, ProfitStatus profitStatus, Pageable pageable);
    Page<BoardDTO.BoardListResponse> getBoardListInfoByUserId(Long memberId, BoardType boardType,Pageable pageable);
    Page<BoardInfoDTO> findAllBySearchCondition(SearchCondition condition, Pageable pageable);
}
