package f3f.domain.bookmark.application;

import f3f.domain.bookmark.dao.BookmarkRepository;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.portfolio.dao.PortfolioRepository;
import f3f.domain.user.dao.MemberRepository;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 구현 기능
 * 1. 팔로우
 * 2. 팔로우 취소
 * 3. 팔로우 목록 조회
 * 4. 팔로잉 목록 조회
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class BookmarkService extends BaseTimeEntity {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void followRequest(BookmarkDTO.BookmarkRequestDto requestDto){
        if(requestDto.getFollower_id().equals(requestDto.getFollowing_id())){
            throw new GeneralException(ErrorCode.MISMATCH_FOLLOW, "본인을 팔로우할 수 없습니다.");
        }
        if(!memberRepository.existsById(requestDto.getFollower_id())){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다.");
        }
        if(!memberRepository.existsById(requestDto.getFollowing_id())){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다.");
        }
        bookmarkRepository.saveFollowRequest(requestDto.getFollower_id(),requestDto.getFollowing_id());

    }


}
