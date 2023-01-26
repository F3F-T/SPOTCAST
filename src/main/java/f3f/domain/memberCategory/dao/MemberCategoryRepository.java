package f3f.domain.memberCategory.dao;

import f3f.domain.memberCategory.domain.MemberCategory;
import f3f.domain.message.domain.Message;
import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {

    List<MemberCategory> findByMemberId(Long memberId);
}
