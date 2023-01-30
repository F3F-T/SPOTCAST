package f3f.domain.message.dao;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.message.domain.Message;
import f3f.domain.message.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static f3f.domain.message.domain.QMessage.message;
import static f3f.domain.user.domain.QMember.member;

@Repository
public class SearchMessageRepositoryImpl extends QuerydslRepositorySupport implements SearchMessageRepository  {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param jpaQueryFactory
     */
    public SearchMessageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Message.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public Page<MessageDTO.MessageListResponseDto> getSendListByUserId(long memberId, Pageable pageable) {

        JPQLQuery<MessageDTO.MessageListResponseDto> query = querydsl().applyPagination(pageable,jpaQueryFactory
                .select(Projections.constructor(MessageDTO.MessageListResponseDto.class,
                        message.id,
                        message.title,
                        message.content,
                        message.createdDate,
                        member.id,
                        member.email,
                        member.name,
                        member.profile))
                .from(member).leftJoin(message).fetchJoin()
                .on(message.recipient.id.eq(member.id))
                .where(message.sender.id.eq(memberId),message.senderDisplayStatus.eq(true))
                .orderBy(new OrderSpecifier(Order.DESC,message.createdDate)));

        long total = query.fetchCount();
        List<MessageDTO.MessageListResponseDto> result = query.fetch();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Page<MessageDTO.MessageListResponseDto> getRecipientListByUserId(long memberId, Pageable pageable) {

        JPQLQuery<MessageDTO.MessageListResponseDto> query = querydsl().applyPagination(pageable, jpaQueryFactory
                .select(Projections.constructor(MessageDTO.MessageListResponseDto.class,
                        message.id,
                        message.title,
                        message.content,
                        message.createdDate,
                        member.id,
                        member.email,
                        member.name,
                        member.profile))
                .from(member).leftJoin(message).fetchJoin()
                .on(message.sender.id.eq(member.id))
                .where(message.recipient.id.eq(memberId),message.recipientDisplayStatus.eq(true))
                .orderBy(new OrderSpecifier(Order.DESC,message.createdDate)));

        long total = query.fetchCount();
        List<MessageDTO.MessageListResponseDto> result = query.fetch();

        return new PageImpl<>(result, pageable, total);
    }
    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}
