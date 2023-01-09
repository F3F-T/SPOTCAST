package f3f.domain.message.dao;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.message.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchMessageRepository {
    // 표시여부에 따라 검색
    // 메시지 최신순으로 검색

    List<MessageDTO.MessageResponseDto> getSendListByCategoryId(long categoryId);
    List<MessageDTO.MessageResponseDto> getRecipientListByUserId(long memberId);
    Page<MessageDTO.MessageResponseDto> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable);
}
