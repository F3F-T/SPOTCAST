package f3f.domain.scrap.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.model.Authority;
import f3f.domain.model.LoginMemberType;
import f3f.domain.model.LoginType;
import f3f.domain.scrap.dao.ScrapBoardRepository;
import f3f.domain.scrap.dao.ScrapRepository;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static f3f.domain.model.BoardType.GENERAL;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class ScrapServiceTest {

    @Autowired
    ScrapRepository scrapRepository;

    @Autowired
    ScrapService scrapService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Autowired
    ScrapBoardRepository scrapBoardRepository;

    @Autowired
    ScrapBoardService scrapBoardService;

    @Autowired
    BoardRepository boardRepository;

    public static final String EMAIL = "test123@test.com";
    public static final String PASSWORD = "test1234";
    public static final String PHONE = "01011112222";
    public static final String INFORMATION = "test";
    public static final String NAME = "lim";
    public static final String NICKNAME = "dong";

    private MemberDTO.MemberSaveRequestDto createMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .phone(PHONE)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information(INFORMATION)
                .name(NAME)
                .nickname(NICKNAME)
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberLoginRequestDto createLoginRequest() {
        MemberDTO.MemberLoginRequestDto memberLoginRequest = MemberDTO.MemberLoginRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        return memberLoginRequest;
    }

    private BoardDTO.SaveRequest createBoardRequest(Member member) {
        BoardDTO.SaveRequest boardSaveRequest = BoardDTO.SaveRequest.builder()
                .title("test")
                .boardType(GENERAL)
                .content("content")
                .member(member)
                .build();
        return boardSaveRequest;
    }


    @Before
    public void setupData() {

    }

    @Test
    @DisplayName("스크랩 박스 생성 성공")
    public void success_SaveScrapBox() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberDto = createMemberDto();
        Long memberId = memberService.saveMember(memberDto);

        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();

        //when
        Scrap scrap = scrapService.saveScrapBox(saveRequest, member.getId());

        //then
        Assertions.assertThat(scrap.getName()).isEqualTo(saveRequest.getName());
        Assertions.assertThat(scrap.getMember()).isEqualTo(member);

    }

    @Test
    @DisplayName("스크랩 박스 생성 실패")
    public void fail_SaveScrapBox() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스크랩 박스 삭제 성공")
    public void success_DeleteScrapBox() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스크랩 박스 삭제 실패")
    public void fail_DeleteScrapBox() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스크랩 박스 이름 수정 성공")
    public void success_UpdateScrapBox() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스크랩 박스 이름 수정 실패")
    public void fail_UpdateScrapBox() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스크랩 박스 조회 성공")
    public void success_GetScrapBoxList() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("스크랩 박스 조회 실패")
    public void fail_GetScrapBoxList() throws Exception {
        //given

        //when

        //then
    }
}
