package f3f.domain.message.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.message.domain.Message;
import f3f.domain.message.domain.QMessage;
import f3f.domain.message.dto.MessageDTO;
import f3f.domain.user.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static f3f.domain.board.domain.QBoard.board;
import static f3f.domain.message.domain.QMessage.message;
import static f3f.domain.user.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class SearchMessageRepositoryImpl implements SearchMessageRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Message> getSendListByUserId(long memberId) {

        List<Message> result = jpaQueryFactory
                .select(Projections.fields(Message.class,
                        message.id,
                        message.content,
                        message.recipient,
                        message.sender))
                .from(message)
                .where(message.sender.id.eq(memberId))
                .fetch();
        return result;
    }

    @Override
    public List<Message> getRecipientListByUserId(long memberId) {

        List<Message> result = jpaQueryFactory
                .select(Projections.fields(Message.class,
                        message.id,
                        message.content,
                        message.recipient,
                        message.sender))
                .from(message)
                .where(message.recipient.id.eq(memberId))
                .fetch();
        return result;
    }

    @Override
    public Page<MessageDTO.MessageResponseDto> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable) {
        return null;
    }
}
