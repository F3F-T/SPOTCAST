package f3f.domain.bookmark.application;

import f3f.domain.bookmark.dao.BookmarkRepository;
import f3f.domain.bookmark.dao.SearchBookmarkRepository;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.user.dao.MemberRepository;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    private final SearchBookmarkRepository searchBookmarkRepository;

    // 북마크 요청
    @Transactional
    public void followRequest(BookmarkDTO.BookmarkRequestDto requestDto, Long currentMemberId){
        if(!requestDto.getFollowerId().equals(currentMemberId)){
            throw new GeneralException(ErrorCode.MISMATCH_FOLLOW, "본인만 팔로우를 할 수 있습니다.");
        }
        if(requestDto.getFollowerId().equals(requestDto.getFollowingId())){
            throw new GeneralException(ErrorCode.MISMATCH_FOLLOW, "본인을 팔로우할 수 없습니다.");
        }
        if(!memberRepository.existsById(requestDto.getFollowerId())){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자의 요청입니다.");
        }
        if(!memberRepository.existsById(requestDto.getFollowingId())){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다.");
        }
        bookmarkRepository.saveFollowRequest(requestDto.getFollowerId(), requestDto.getFollowingId());

    }

    //북마크 취소 요청
    @Transactional
    public void followCancel(BookmarkDTO.BookmarkRequestDto requestDto, Long currentMemberId){
        if(!requestDto.getFollowerId().equals(currentMemberId)){
            throw new GeneralException(ErrorCode.MISMATCH_FOLLOW, "본인만 팔로우를 취소할 수 있습니다.");
        }

        bookmarkRepository.deleteByFollowerIdAndFollowingId(requestDto.getFollowerId(),requestDto.getFollowingId());
    }

    //나를 팔로우하는 사람들 리스트
    @Transactional
    public Slice<BookmarkDTO.BookmarkListResponseDto> getFollowerList(Long memberId, Pageable pageable){
        Slice<BookmarkDTO.BookmarkListResponseDto> followerList = searchBookmarkRepository.getFollowerListByMemberId(memberId, pageable);
        return followerList;
    }

    //내가 팔로우하는 사람들 리스트
    @Transactional
    public Slice<BookmarkDTO.BookmarkListResponseDto> getFollowingList(Long memberId,Pageable pageable){
        Slice<BookmarkDTO.BookmarkListResponseDto> followingList = searchBookmarkRepository.getFollowingListByMemberId(memberId,pageable);
        return followingList;
    }
}
