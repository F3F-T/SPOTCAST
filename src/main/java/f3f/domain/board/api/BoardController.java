package f3f.domain.board.api;


import f3f.domain.board.application.BoardService;
import f3f.domain.board.domain.Board;
import f3f.domain.board.domain.ProfitStatus;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.BoardListResponse;
import f3f.domain.publicModel.BoardType;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static f3f.global.response.ResultDataResponseDTO.of;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 생성
    @PostMapping(value = "/board")
    public ResultDataResponseDTO<Long> saveBoard(@RequestBody BoardDTO.SaveRequest request) {
        return of(boardService.saveBoard(request));
    }

    //게시글 수정
    @PutMapping(value = "/board/{boardId}")
    public ResultDataResponseDTO<BoardInfoDTO> updateBoard(@PathVariable long boardId, @RequestBody BoardDTO.SaveRequest request) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Board board = boardService.updateBoard(boardId, memberId, request);
        return of(board.toBoardInfoDTO());
    }

    //게시글 삭제
    @DeleteMapping(value = "/board/{boardId}")
    public ResultDataResponseDTO<Long> deleteBoard(@PathVariable long boardId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Board board = boardService.deleteBoard(boardId, memberId);
        return of(board.getId());
    }

    //(게시글 리스트) 게시글 타입 , 카테고리 식별자,수익여부,정렬
    @GetMapping(value = "/board/list/boardType/{boardType}")
    public ResultDataResponseDTO<Page<BoardListResponse>> getBoardListByCategoryId(@PathVariable String boardType, @RequestParam Long categoryId,
                                                                                   @RequestParam String profitStatus, Pageable pageable) {

        return of(boardService.getBoardListByCategoryId(boardType, categoryId, profitStatus, pageable));
    }

    //(마이 페이지 )유저 식별자, 게시글 타입 , 수익 여부,정렬
    @GetMapping(value = "/board/list/memberInfo/{memberId}")
    public ResultDataResponseDTO<Page<BoardListResponse>> getBoardListByMemberId(@PathVariable Long memberId, @RequestParam String boardType,
                                                                                 @RequestParam String profitStatus,  Pageable pageable) {
        return of(boardService.getBoardListByMemberId(memberId, boardType, profitStatus,pageable));
    }

    //게시글 조회
    @GetMapping(value = "/board/{boardId}")
    public ResultDataResponseDTO<BoardInfoDTO> getBoardInfo(@PathVariable long boardId) {
        return of(boardService.getBoardInfo(boardId));
    }

    @GetMapping(value = "/board/list")
    public List<BoardInfoDTO> getBoardInfoList() {
        return null;
    }

}
