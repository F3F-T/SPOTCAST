package f3f.domain.memberCategory.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.memberCategory.dto.MemberCategoryDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static f3f.domain.category.domain.QCategory.category;
import static f3f.domain.memberCategory.domain.QMemberCategory.memberCategory;

@Repository
public class MemberCategoryRepositoryImpl implements MemberCategoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberCategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<MemberCategoryDTO.categoryResponseDto> findCategoryByMemberId(Long memberId) {

        List<MemberCategoryDTO.categoryResponseDto> list = jpaQueryFactory
                .select(Projections.constructor(MemberCategoryDTO.categoryResponseDto.class,
                        category.id,
                        category.name,
                        category.depth,
                        category.parentCategory.id))
                .from(category).leftJoin(memberCategory).fetchJoin()
                .on(memberCategory.category.id.eq(category.id))
                .where(memberCategory.member.id.eq(memberId)).fetch();


        return list;
    }

    @Override
    public List<MemberCategoryDTO.CategoryMyInfo> findChildCategoryByName(String name) {
        List<MemberCategoryDTO.CategoryMyInfo> list = jpaQueryFactory
                .select(Projections.constructor(MemberCategoryDTO.CategoryMyInfo.class,
                        category.id,
                        category.name))
                .from(category)
                .where(category.parentCategory.name.eq(name)).fetch();


        return list;
    }


}
