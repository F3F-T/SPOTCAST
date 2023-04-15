package f3f.domain.memberCategory.dao;

import f3f.domain.memberCategory.dto.MemberCategoryDTO;

import java.util.List;


public interface MemberCategoryRepository {

    List<MemberCategoryDTO.categoryResponseDto> findCategoryByMemberId(Long memberId);

    List<MemberCategoryDTO.CategoryMyInfo> findChildCategoryByName(String name);
}
