package f3f.domain.message.dao;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.message.domain.Message;
import f3f.domain.message.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchMessageRepository {
    // 표시여부에 따라 검색
    // 메시지 최신순으로 검색

    Page<MessageDTO.MessageListResponseDto> getSendListByUserId(long sender_id, Pageable pageable);

    Page<MessageDTO.MessageListResponseDto> getRecipientListByUserId(long recipient_id, Pageable pageable);
//    Page<MessageDTO.MessageResponseDto> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable);
}
