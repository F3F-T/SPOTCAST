package f3f.domain.user.dao;

import f3f.domain.user.dto.MemberDTO;

import java.util.List;

public interface MemberRepositoryDao {

    MemberDTO.MemberInfoResponseDto getMemberInfo(Long memberId);
}
