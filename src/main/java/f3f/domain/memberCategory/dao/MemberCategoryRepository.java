package f3f.domain.memberCategory.dao;

import f3f.domain.category.domain.Category;
import f3f.domain.memberCategory.domain.MemberCategory;
import f3f.domain.memberCategory.dto.MemberCategoryDTO;
import f3f.domain.message.domain.Message;
import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface MemberCategoryRepository {

    List<MemberCategoryDTO.categoryResponseDto> findCategoryByMemberId(Long memberId);
}
