package f3f.domain.memberCategory.dao;

import f3f.domain.memberCategory.domain.MemberCategory;
import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberCategoryJpaRepository extends JpaRepository<MemberCategory,Long> {

    Optional<MemberCategory> findByCategoryIdAndMemberId(Long categoryId, Long memberId);
}
