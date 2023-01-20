package f3f.domain.bookmark.application;

import f3f.domain.bookmark.dao.BookmarkRepository;
import f3f.domain.bookmark.dao.SearchBookmarkRepository;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.bookmark.dto.BookmarkDTO;
import f3f.domain.bookmark.dto.BookmarkDTO.BookmarkRequestDto;
import f3f.domain.publicModel.Authority;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.publicModel.LoginType;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.dto.MemberDTO;
import f3f.global.response.GeneralException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
@Transactional
public class BookmarkServiceTest {
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SearchBookmarkRepository searchBookmarkRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    BookmarkService bookmarkService;


    public static final String PASSWORD = "test1234";
    public static final String NAME = "lim";

    private Long followerId;
    private List<Long> followingId = new ArrayList<>();

    private MemberDTO.MemberSaveRequestDto createFollowerMember(String EMAIL) {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name(NAME)
                .build();
        return memberSaveRequestDto;
    }


    private MemberDTO.MemberSaveRequestDto createFollowingMember(String EMAIL) {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("NAME")
                .build();
        return memberSaveRequestDto;
    }

    private BookmarkRequestDto createBookmarkRequest(Long followerId, Long followingId) {
        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDto.builder()
                .followingId(followingId)
                .followerId(followerId)
                .build();
        return bookmarkRequestDto;
    }

    @BeforeEach
    public void setup() {

        MemberDTO.MemberSaveRequestDto followerMember = createFollowerMember("test@naver.com");
        followerId = memberService.saveMember(followerMember);

        followingId.clear();
        for (int i = 0; i < 10; i++) {
            MemberDTO.MemberSaveRequestDto followingMember = createFollowingMember(i + "test@naver.com");
            Long saveMember = memberService.saveMember(followingMember);
            System.out.println("saveMember = " + saveMember);

            followingId.add(saveMember);
        }
    }

    @Test
    @DisplayName("북마크 요청 성공")
    public void success_followRequest() throws Exception {
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, followingId.get(0));

        //when
        bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId());

        Bookmark bookmark = bookmarkRepository.findByFollowerIdAndFollowingId(bookmarkRequest.getFollowerId(), bookmarkRequest.getFollowingId()).orElse(null);
        //then
        Assertions.assertThat(bookmark.getFollowing().getId()).isEqualTo(bookmarkRequest.getFollowingId());
        Assertions.assertThat(bookmark.getFollower().getId()).isEqualTo(bookmarkRequest.getFollowerId());
    }

    @Test
    @DisplayName("북마크 요청 실패 - bookmark 요청한 사람이 다른 사람일 경우")
    public void fail_followRequest_MismatchFollower() throws Exception {
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, followingId.get(0));

        //when

        //then
        assertThrows(GeneralException.class, () ->
                bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowingId()));
    }

    @Test
    @DisplayName("북마크 요청 실패 - 본인을 팔로우한경우")
    public void fail_followRequest_MismatchFollow() throws Exception {
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, followerId);

        //when

        //then
        assertThrows(GeneralException.class, () ->
                bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId()));
    }

    @Test
    @DisplayName("북마크 요청 실패 - follower 가 존재하지 않는 경우")
    public void fail_followRequest_NotFoundFollower() throws Exception {
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(12398213921L, followerId);

        //when

        //then
        assertThrows(GeneralException.class, () ->
                bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId()));
    }

    @Test
    @DisplayName("북마크 요청 실패 - following mmeber 가 존재하지 않는 경우")
    public void fail_followRequest_NotFoundFollowing() throws Exception {
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, 123123213L);

        //when

        //then
        assertThrows(GeneralException.class, () ->
                bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId()));
    }

    @Test
    @DisplayName("북마크 취소 성공")
    public void success_followCancel() throws Exception{
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, followingId.get(0));
        bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId());

        //when
        bookmarkService.followCancel(bookmarkRequest, bookmarkRequest.getFollowerId());
        Bookmark bookmark = bookmarkRepository.findByFollowerIdAndFollowingId(bookmarkRequest.getFollowerId(), bookmarkRequest.getFollowingId()).orElse(null);

        //then
        Assertions.assertThat(bookmark).isNull();
    }
    @Test
    @DisplayName("북마크 취소 실패 - 본인이 요청한 취소요청이 아닌 경우")
    public void fail_followCancel_MisMatchRequest() throws Exception{
        //given
        BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, followingId.get(0));
        bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId());

        //when


        //then
        assertThrows(GeneralException.class, () ->
                bookmarkService.followCancel(bookmarkRequest, bookmarkRequest.getFollowingId()));
    }

    @Test
    @DisplayName("나를 북마크중인 리스트 조회 성공")
    public void success_getFollowerList() throws Exception{
        //given
        for (Long following : followingId) {
            BookmarkRequestDto bookmarkRequest = createBookmarkRequest(followerId, following);
            bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId());
        }

        //when
        PageRequest pageable = PageRequest.of(0, followingId.size());
        Slice<BookmarkDTO.BookmarkListResponseDto> followerList = bookmarkService.getFollowerList(followerId, pageable);

        //then
        Assertions.assertThat(followerList.getSize()).isEqualTo(followingId.size());
    }

    @Test
    @DisplayName("내가 북마크중인 리스트 조회 성공")
    public void success_getFollowingList() throws Exception{
        //given
        for (Long following : followingId) {
            BookmarkRequestDto bookmarkRequest = createBookmarkRequest(following, followerId);
            bookmarkService.followRequest(bookmarkRequest, bookmarkRequest.getFollowerId());
        }

        //when
        PageRequest pageable = PageRequest.of(0, followingId.size());
        Slice<BookmarkDTO.BookmarkListResponseDto> followerList = bookmarkService.getFollowingList(followerId, pageable);

        //then
        Assertions.assertThat(followerList.getSize()).isEqualTo(followingId.size());
    }


}
