package f3f.domain.user.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.domain.user.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static f3f.domain.bookmark.domain.QBookmark.bookmark;
import static f3f.domain.user.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryDaoImpl implements MemberRepositoryDao{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MemberDTO.MemberInfoResponseDto getMemberInfo(Long memberId) {


        MemberDTO.MemberInfoResponseDto infoResponseDto = jpaQueryFactory
                .select(Projections.constructor(
                        MemberDTO.MemberInfoResponseDto.class,
                        member.id,
                        member.email,
                        member.name,
                        member.twitter,
                        member.instagram,
                        member.otherSns,
                        member.egName,
                        member.loginMemberType,
                        member.loginType,
                        member.authority,
                        member.information,
                        member.profile,
                        member.followingList.size(),
                        member.followerList.size()
                ))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return infoResponseDto;

    }
}
