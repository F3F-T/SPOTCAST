package f3f.domain.bookmark.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.xml.bind.v2.TODO;
import f3f.domain.bookmark.dto.BookmarkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static f3f.domain.bookmark.domain.QBookmark.bookmark;
import static f3f.domain.user.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class SearchBookmarkRepositoryImpl implements SearchBookmarkRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO 쿼리 튜닝 해야함
    @Override
    public List<BookmarkDTO.BookmarkListResponseDto> getFollowerListByMemberId(Long member_id) {
        List<BookmarkDTO.BookmarkListResponseDto> followerList = jpaQueryFactory
                .select(Projections.constructor(
                        BookmarkDTO.BookmarkListResponseDto.class,bookmark.id, member.id, member.email, member.name))
                .from(member).leftJoin(bookmark).fetchJoin()
                .on(bookmark.follower.id.eq(member.id))
                .where(bookmark.following.id.eq(member_id))
                .fetch();
        return followerList;
    }

    @Override
    public List<BookmarkDTO.BookmarkListResponseDto> getFollowingListByMemberId(Long member_id) {
        List<BookmarkDTO.BookmarkListResponseDto> followerList = jpaQueryFactory
                .select(Projections.constructor(
                        BookmarkDTO.BookmarkListResponseDto.class, bookmark.id,member.id, member.email, member.name))
                .from(member).join(bookmark).fetchJoin()
                .on(bookmark.following.id.eq(member.id))
                .where(bookmark.follower.id.eq(member_id))
                .fetch();
        return followerList;
    }
}
