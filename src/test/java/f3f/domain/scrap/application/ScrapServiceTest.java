//package f3f.domain.scrap.application;
//
//import f3f.domain.board.application.BoardService;
//import f3f.domain.board.dao.BoardRepository;
//
//import f3f.domain.board.dto.BoardDTO;
//import f3f.domain.category.application.CategoryService;
//
//import f3f.domain.publicModel.Authority;
//import f3f.domain.publicModel.LoginMemberType;
//import f3f.domain.publicModel.LoginType;
//
//import f3f.domain.scrap.dao.ScrapRepository;
//import f3f.domain.scrap.domain.Scrap;
//import f3f.domain.scrap.dto.ScrapDTO;
//import f3f.domain.scrapBoard.application.ScrapBoardService;
//import f3f.domain.scrapBoard.dao.ScrapBoardRepository;
//import f3f.domain.user.application.MemberService;
//import f3f.domain.user.dao.MemberRepository;
//import f3f.domain.user.domain.Member;
//import f3f.domain.user.dto.MemberDTO;
//
//import f3f.global.response.GeneralException;
//import org.junit.Before;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//
//import static f3f.domain.publicModel.BoardType.GENERAL;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@Transactional
//@WebAppConfiguration
//@RunWith(SpringRunner.class)
//public class ScrapServiceTest {
//
//    @Autowired
//    ScrapRepository scrapRepository;
//
//    @Autowired
//    ScrapService scrapService;
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//
//    @Autowired
//    ScrapBoardRepository scrapBoardRepository;
//
//    @Autowired
//    ScrapBoardService scrapBoardService;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    BoardService boardService;
//
//    @Autowired
//    CategoryService categoryService;
//
//    public static final String EMAIL = "test123@test.com";
//    public static final String PASSWORD = "test1234";
//    public static final String PHONE = "01011112222";
//    public static final String INFORMATION = "test";
//    public static final String NAME = "lim";
//    public static final String NICKNAME = "dong";
//
//    private MemberDTO.MemberSaveRequestDto createMemberDto() {
//        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
//                .email(EMAIL)
//                .password(PASSWORD)
//                .authority(Authority.ROLE_USER)
//                .loginMemberType(LoginMemberType.GENERAL_USER)
//                .loginType(LoginType.GENERAL_LOGIN)
//                .name(NAME)
//                .build();
//        return memberSaveRequestDto;
//    }
//
//    private MemberDTO.MemberLoginRequestDto createLoginRequest() {
//        MemberDTO.MemberLoginRequestDto memberLoginRequest = MemberDTO.MemberLoginRequestDto.builder()
//                .email(EMAIL)
//                .password(PASSWORD)
//                .build();
//
//        return memberLoginRequest;
//    }
//
//    private BoardDTO.SaveRequest createBoardRequest(Member member) {
//
////        Category category = Category.builder()
////                .name("test")
////                .depth(0)
////                .parentCategory(null)
////                .build();
////
////        CategoryDTO.SaveRequest.buil
////
////        categoryService.saveCategory(request);
//        BoardDTO.SaveRequest boardSaveRequest = BoardDTO.SaveRequest.builder()
//                .title("test")
//                .boardType(GENERAL)
//                .content("content")
//                .member(member)
////                .category(category)
//                .build();
//        return boardSaveRequest;
//    }
//
//
//    @Before
//    public void setupData() {
//
//        Long memberId = 1L;
//        Member member = memberRepository.findById(memberId).get();
//
//        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
//        boardRepository.save(boardRequest.toEntity());
//    }
//
//
//    @Test
//    @DisplayName("스크랩 박스 생성 성공")
//    public void success_SaveScrapBox() throws Exception {
//        //given
//
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//
//        //when
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//
//        //then
//        assertThat(scrap.getName()).isEqualTo(saveRequest.getName());
//        assertThat(scrap.getMember().getId()).isEqualTo(memberId);
//
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 생성 실패 - 유저 존재 X")
//    public void fail_SaveScrapBox_MemberNotFound() throws Exception {
//        //given
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//
//        //when
//
//
//        //then
//        assertThrows(GeneralException.class, () ->
//                scrapService.saveScrapBox(saveRequest,111111L));
//
//    }
//
//
//    @Test
//    @DisplayName("스크랩 박스 삭제 성공")
//    public void success_DeleteScrapBox() throws Exception {
//        //given
//        Long memberId = 1L;
//
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//        Long scrapId = scrap.getId();
//        ScrapDTO.ScrapDeleteRequestDTO deleteRequestDTO = ScrapDTO.ScrapDeleteRequestDTO.builder()
//                .scrapId(scrapId)
//                .build();
//        //when
//        scrapService.deleteScrapBox(deleteRequestDTO, memberId);
//
//        //then
//        assertThat(scrapRepository.findById(scrapId)).isEmpty();
//        assertThat(scrapBoardRepository.findByScrapId(scrapId).size()).isEqualTo(0);
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 삭제 실패 - 존재하지 않는 사용자")
//    public void fail_DeleteScrapBox_MemberNotFound() throws Exception {
//        //given
//        Long memberId = 1L;
//
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//        Long scrapId = scrap.getId();
//        ScrapDTO.ScrapDeleteRequestDTO deleteRequestDTO = ScrapDTO.ScrapDeleteRequestDTO.builder()
//                .scrapId(scrapId)
//                .build();
//        //when
//
//
//        //then
//        assertThrows(GeneralException.class, () ->
//                scrapService.deleteScrapBox(deleteRequestDTO, 111111111L));
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 삭제 실패 - 존재하지 않는 스크랩")
//    public void fail_DeleteScrapBox_ScrapNotFound() throws Exception {
//        //given
//        Long memberId = 1L;
//
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//        ScrapDTO.ScrapDeleteRequestDTO deleteRequestDTO = ScrapDTO.ScrapDeleteRequestDTO.builder()
//                .scrapId(1111111111L)
//                .build();
//        //when
//
//
//        //then
//        assertThrows(GeneralException.class, () ->
//                scrapService.deleteScrapBox(deleteRequestDTO, memberId));
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 삭제 실패 - 스크랩을 한 member의 id와 요청한 member id 가 다를 경우")
//    public void fail_DeleteScrapBox_ScrapMissMatchMember() throws Exception {
//        //given
//        Long memberId = 1L;
//
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//        Long scrapId = scrap.getId();
//        ScrapDTO.ScrapDeleteRequestDTO deleteRequestDTO = ScrapDTO.ScrapDeleteRequestDTO.builder()
//                .scrapId(scrapId)
//                .build();
//        //when
//
//
//        //then
//        assertThrows(GeneralException.class, () ->
//                scrapService.deleteScrapBox(deleteRequestDTO, 2L));
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 이름 수정 성공")
//    public void success_UpdateScrapBox() throws Exception {
//        //given
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//
//        //when
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//
//        Long scrapId = scrap.getId();
//
//        ScrapDTO.UpdateRequest updateRequest = ScrapDTO.UpdateRequest.builder()
//                .scrapId(scrapId)
//                .name("test2")
//                .build();
//
//
//        scrapService.updateScrapBox(updateRequest,memberId);
//
//        //then
//        assertThat(scrapRepository.findById(scrapId).get().getName()).isEqualTo(updateRequest.getName());
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 이름 수정 실패 - member 존재 X")
//    public void fail_UpdateScrapBox_MemberNotFound() throws Exception {
//        //given
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//
//        //when
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//
//        Long scrapId = scrap.getId();
//
//        ScrapDTO.UpdateRequest updateRequest = ScrapDTO.UpdateRequest.builder()
//                .scrapId(scrapId)
//                .build();
//
//
//
//        //then
//        assertThrows(GeneralException.class, () ->
//                scrapService.updateScrapBox(updateRequest,111111111L));
//    }
//
//
//
//
//    @Test
//    @DisplayName("스크랩 박스 이름 수정 실패 - scrap 존재 X")
//    public void fail_UpdateScrapBox_ScrapNotFound() throws Exception {
//        //given
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//
//        //when
//        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
//
//        Long scrapId = scrap.getId();
//
//        ScrapDTO.UpdateRequest updateRequest = ScrapDTO.UpdateRequest.builder()
//                .scrapId(11111111111L)
//                .name("test2")
//                .build();
//
//
//        //then
//        assertThrows(GeneralException.class, () ->
//                scrapService.updateScrapBox(updateRequest,memberId));
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 조회 성공")
//    public void success_GetScrapBoxList() throws Exception {
//        //given
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest1 = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//        scrapService.saveScrapBox(saveRequest1, memberId);
//
//        ScrapDTO.SaveRequest saveRequest2 = ScrapDTO.SaveRequest.builder()
//                .name("test2")
//                .build();
//        scrapService.saveScrapBox(saveRequest2, memberId);
//
//        //when
//        List<ScrapDTO.ScrapInfoDTO> scrapBoxList = scrapService.getScrapBoxList(memberId);
//
//        //then
//        assertThat(scrapBoxList.size()).isEqualTo(2);
//
//    }
//
//    @Test
//    @DisplayName("스크랩 박스 조회 실패 - member 존재 X")
//    public void fail_GetScrapBoxList_MemberNotFound() throws Exception {
//        //given
//        Long memberId = 1L;
//        ScrapDTO.SaveRequest saveRequest1 = ScrapDTO.SaveRequest.builder()
//                .name("test1")
//                .build();
//        scrapService.saveScrapBox(saveRequest1, memberId);
//
//        ScrapDTO.SaveRequest saveRequest2 = ScrapDTO.SaveRequest.builder()
//                .name("test2")
//                .build();
//        scrapService.saveScrapBox(saveRequest2, memberId);
//
//        //when
//
//
//        //then
//        assertThrows(GeneralException.class,() ->
//                scrapService.getScrapBoxList(11111111111L));
//    }
//}
