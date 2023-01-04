package f3f.domain.scrap.application;

import f3f.domain.board.application.BoardService;
import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.exception.NotFoundBoardException;
import f3f.domain.category.application.CategoryService;

import f3f.domain.scrap.dao.ScrapRepository;
import f3f.domain.scrap.domain.Scrap;

import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrap.exception.ScrapBoardMissMatchMemberException;
import f3f.domain.scrap.exception.ScrapNotFoundException;
import f3f.domain.scrapBoard.application.ScrapBoardService;
import f3f.domain.scrapBoard.dao.ScrapBoardRepository;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import f3f.domain.scrapBoard.dto.ScrapBoardDTO;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static f3f.domain.publicModel.BoardType.GENERAL;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ScrapBoardServiceTest {

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

    @Autowired
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    private BoardDTO.SaveRequest createBoardRequest(Member member) {

        BoardDTO.SaveRequest boardSaveRequest = BoardDTO.SaveRequest.builder()
                .title("test")
                .boardType(GENERAL)
                .content("content")
                .member(member)
                .build();
        return boardSaveRequest;
    }

    @Test
    @DisplayName("스크랩 생성 성공")
    public void success_SaveScrap() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();
        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();
        //when
        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest);
        Long scrapBoardId = scrapBoard.getId();

        //then
        Long findBoardId = scrapBoardRepository.findById(scrapBoardId).get().getBoard().getId();
        Long findScrapId = scrapBoardRepository.findById(scrapBoardId).get().getScrap().getId();

        assertThat(findScrapId).isEqualTo(scrapId);
        assertThat(findBoardId).isEqualTo(boardId);

    }

    @Test
    @DisplayName("스크랩 생성 실패 - member 존재 X")
    public void fail_SaveScrap_MemberNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();
        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();

        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        //when

        //then
        assertThrows(MemberNotFoundException.class, () ->
                scrapBoardService.saveScrap(1111111111L, scrapId, boardSaveRequest));
    }

    @Test
    @DisplayName("스크랩 생성 실패 - scrapBox 존재 X")
    public void fail_SaveScrap_ScrapNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();

        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        //when

        //then
        assertThrows(ScrapNotFoundException.class, () ->
                scrapBoardService.saveScrap(memberId, 1111111111111L, boardSaveRequest));
    }

    @Test
    @DisplayName("스크랩 생성 실패 - board 존재 X")
    public void fail_SaveScrap_BoardNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(11111111111L).build();

        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        //when

        //then
        assertThrows(NotFoundBoardException.class, () ->
                scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest));
    }

    @Test
    @DisplayName("스크랩 삭제 성공")
    public void success_DeleteScrap() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest);
        Long scrapBoardId = scrapBoard.getId();
        ScrapBoardDTO.DeleteRequest deleteRequest = ScrapBoardDTO.DeleteRequest.builder()
                .id(scrapBoardId)
                .build();
        //when
        scrapBoardService.deleteScrap(memberId, scrapId, deleteRequest);

        //then
        assertThat(scrapBoardRepository.findById(scrapBoardId)).isEmpty();

    }

    @Test
    @DisplayName("스크랩 삭제 실패 - member 존재 X")
    public void fail_DeleteScrap_MemberNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest);
        Long scrapBoardId = scrapBoard.getId();
        ScrapBoardDTO.DeleteRequest deleteRequest = ScrapBoardDTO.DeleteRequest.builder()
                .id(scrapBoardId)
                .build();
        //when

        //then
        assertThrows(MemberNotFoundException.class, () ->
                scrapBoardService.deleteScrap(111111111L, scrapId, deleteRequest));
    }

    @Test
    @DisplayName("스크랩 삭제 실패 - scrap 존재 X")
    public void fail_DeleteScrap_ScrapNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest);
        Long scrapBoardId = scrapBoard.getId();
        ScrapBoardDTO.DeleteRequest deleteRequest = ScrapBoardDTO.DeleteRequest.builder()
                .id(scrapBoardId)
                .build();
        //when

        //then
        assertThrows(ScrapNotFoundException.class, () ->
                scrapBoardService.deleteScrap(memberId, 11111111L, deleteRequest));
    }

    @Test
    @DisplayName("스크랩 삭제 실패 - scrap member와 member id 로 찾은 member missmatch")
    public void fail_DeleteScrap_ScrapBoardMissMatchMember() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest = createBoardRequest(member);
        Board board = boardRepository.save(boardRequest.toEntity());
        Long boardId = board.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest = ScrapBoardDTO.SaveRequest.builder().boardId(boardId).build();
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapDTO.SaveRequest newSaveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap2 = scrapService.saveScrapBox(newSaveRequest, 2L);
        Long scrapId2 = scrap2.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest);
        Long scrapBoardId = scrapBoard.getId();
        ScrapBoardDTO.DeleteRequest deleteRequest = ScrapBoardDTO.DeleteRequest.builder()
                .id(scrapBoardId)
                .build();
        //when

        //then
        assertThrows(ScrapBoardMissMatchMemberException.class, () ->
                scrapBoardService.deleteScrap(memberId, scrapId2, deleteRequest));
    }

    @Test
    @DisplayName("스크랩 목록 조회 성공")
    public void success_GetScrapList() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest1 = createBoardRequest(member);
        Board board1 = boardRepository.save(boardRequest1.toEntity());
        Long boardId1 = board1.getId();
        ScrapBoardDTO.SaveRequest boardSaveRequest1 = ScrapBoardDTO.SaveRequest.builder().boardId(boardId1).build();

        BoardDTO.SaveRequest boardRequest2 = createBoardRequest(member);
        Board board2 = boardRepository.save(boardRequest2.toEntity());
        Long boardId2 = board2.getId();
        ScrapBoardDTO.SaveRequest boardSaveRequest2 = ScrapBoardDTO.SaveRequest.builder().boardId(boardId2).build();

        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest1);
        ScrapBoard scrapBoard2 = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest2);
        Long scrapBoardId = scrapBoard.getId();

        //when
        List<BoardDTO.BoardInfoDTO> scrapList = scrapBoardService.getScrapList(scrapId, memberId);

        //then
        assertThat(scrapList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("스크랩 목록 조회 실패 - member 조회 X")
    public void fail_GetScrapList_MemberNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest1 = createBoardRequest(member);
        Board board1 = boardRepository.save(boardRequest1.toEntity());
        Long boardId1 = board1.getId();
        ScrapBoardDTO.SaveRequest boardSaveRequest1 = ScrapBoardDTO.SaveRequest.builder().boardId(boardId1).build();
        BoardDTO.SaveRequest boardRequest2 = createBoardRequest(member);
        Board board2 = boardRepository.save(boardRequest2.toEntity());
        Long boardId2 = board2.getId();
        ScrapBoardDTO.SaveRequest boardSaveRequest2 = ScrapBoardDTO.SaveRequest.builder().boardId(boardId2).build();
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest1);
        ScrapBoard scrapBoard2 = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest2);
        Long scrapBoardId = scrapBoard.getId();

        //when

        //then

        assertThrows(MemberNotFoundException.class, () ->
                scrapBoardService.getScrapList(scrapId, 1111111111L));

    }

    @Test
    @DisplayName("스크랩 목록 조회 실패 - scrap 조회 X")
    public void fail_GetScrapList_ScrapNotFound() throws Exception {
        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();

        BoardDTO.SaveRequest boardRequest1 = createBoardRequest(member);
        Board board1 = boardRepository.save(boardRequest1.toEntity());
        Long boardId1 = board1.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest1 = ScrapBoardDTO.SaveRequest.builder().boardId(boardId1).build();
        BoardDTO.SaveRequest boardRequest2 = createBoardRequest(member);
        Board board2 = boardRepository.save(boardRequest2.toEntity());
        Long boardId2 = board2.getId();

        ScrapBoardDTO.SaveRequest boardSaveRequest2 = ScrapBoardDTO.SaveRequest.builder().boardId(boardId2).build();
        ScrapDTO.SaveRequest saveRequest = ScrapDTO.SaveRequest.builder()
                .name("test1")
                .build();
        Scrap scrap = scrapService.saveScrapBox(saveRequest, memberId);
        Long scrapId = scrap.getId();

        ScrapBoard scrapBoard = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest1);
        ScrapBoard scrapBoard2 = scrapBoardService.saveScrap(memberId, scrapId, boardSaveRequest2);
        Long scrapBoardId = scrapBoard.getId();

        //when

        //then

        assertThrows(ScrapNotFoundException.class, () ->
                scrapBoardService.getScrapList(1111111111L, memberId));
    }

}
