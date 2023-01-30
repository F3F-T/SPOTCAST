package f3f.domain.bookmark.dao;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.xml.bind.v2.TODO;
import f3f.domain.bookmark.dto.BookmarkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static f3f.domain.bookmark.domain.QBookmark.bookmark;
import static f3f.domain.message.domain.QMessage.message;
import static f3f.domain.user.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class SearchBookmarkRepositoryImpl implements SearchBookmarkRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<BookmarkDTO.BookmarkListResponseDto> getFollowerListByMemberId(Long member_id, Pageable pageable) {
        List<BookmarkDTO.BookmarkListResponseDto> followerList = jpaQueryFactory
                .select(Projections.constructor(
                        BookmarkDTO.BookmarkListResponseDto.class,bookmark.id, member.id, member.email, member.name,member.profile))
                .from(member).leftJoin(bookmark).fetchJoin()
                .on(bookmark.follower.id.eq(member.id))
                .where(bookmark.following.id.eq(member_id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // limit보다 데이터를 1개 더 들고와서, 해당 데이터가 있다면 hasNext 변수에 true를 넣어 알림
                .orderBy(new OrderSpecifier(Order.DESC,bookmark.createdDate))
                .fetch();

        List<BookmarkDTO.BookmarkListResponseDto> content = new ArrayList<>();
        for (BookmarkDTO.BookmarkListResponseDto eachFollower : followerList) {

            content.add(eachFollower);
        }

        boolean hasNext = false;
        if (followerList.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<BookmarkDTO.BookmarkListResponseDto> getFollowingListByMemberId(Long member_id, Pageable pageable) {
        List<BookmarkDTO.BookmarkListResponseDto> followingList = jpaQueryFactory
                .select(Projections.constructor(
                        BookmarkDTO.BookmarkListResponseDto.class, bookmark.id,member.id, member.email, member.name,member.profile))
                .from(member).join(bookmark).fetchJoin()
                .on(bookmark.following.id.eq(member.id))
                .where(bookmark.follower.id.eq(member_id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // limit보다 데이터를 1개 더 들고와서, 해당 데이터가 있다면 hasNext 변수에 true를 넣어 알림
                .orderBy(new OrderSpecifier(Order.DESC,bookmark.createdDate))
                .fetch();

        List<BookmarkDTO.BookmarkListResponseDto> content = new ArrayList<>();
        for (BookmarkDTO.BookmarkListResponseDto eachFollowing : followingList) {

            content.add(eachFollowing);
        }

        boolean hasNext = false;
        if (followingList.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }
}
