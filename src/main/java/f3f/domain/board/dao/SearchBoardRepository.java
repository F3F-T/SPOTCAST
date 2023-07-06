package f3f.domain.board.dao;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Page<BoardDTO.BoardListResponse> getBoardListInfoByCategoryId(String boardType, Long categoryId, String profitStatus, Pageable pageable);
    Page<BoardDTO.BoardListResponse> getBoardList(String boardType, String profitStatus, Pageable pageable);
    Page<BoardDTO.BoardListResponse> getBoardListInfoByMemberId(Long memberId, String boardType, String profitStatus, Pageable pageable);
    Page<BoardInfoDTO> findAllBySearchCondition(SearchCondition condition, Pageable pageable);
}
