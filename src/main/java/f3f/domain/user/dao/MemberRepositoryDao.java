package f3f.domain.user.dao;

import f3f.domain.user.dto.MemberDTO;

public interface MemberRepositoryDao {

    MemberDTO.MemberInfoResponseDto getMemberInfo(Long memberId);
}
