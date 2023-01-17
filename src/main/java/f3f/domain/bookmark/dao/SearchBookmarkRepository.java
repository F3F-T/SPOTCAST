package f3f.domain.bookmark.dao;


import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.domain.message.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchBookmarkRepository {

    //본인을 팔로우하고 있는 리스트
    List<BookmarkDTO.BookmarkListResponseDto> getFollowerListByMemberId(Long member_id);

    //본인이 팔로우하고 있는 리스트
    List<BookmarkDTO.BookmarkListResponseDto> getFollowingListByMemberId(Long member_id);
}
