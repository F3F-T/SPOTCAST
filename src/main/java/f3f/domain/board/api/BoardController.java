package f3f.domain.board.api;


import f3f.domain.board.application.BoardService;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.publicModel.BoardType;
import f3f.domain.publicModel.SortType;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 생성
    @PostMapping(value = "/board")
    public ResultDataResponseDTO<Long> saveBoard(@RequestBody BoardDTO.SaveRequest request){
        return ResultDataResponseDTO.of(boardService.saveBoard(request));
    }


    //게시글 수정
    @PutMapping(value = "/board/{boardId}")
    public ResultDataResponseDTO<BoardDTO.BoardInfoDTO> updateBoard(@PathVariable long boardId,@RequestBody BoardDTO.SaveRequest request){
        Long memberId = SecurityUtil.getCurrentMemberId();
        Board board = boardService.updateBoard(boardId, memberId,request);
        return ResultDataResponseDTO.of(board.toBoardInfoDTO());
    }

    //게시글 삭제
    @DeleteMapping(value = "/board/{boardId}")
    public ResultDataResponseDTO<Long> deleteBoard(@PathVariable long boardId){
        Long memberId = SecurityUtil.getCurrentMemberId();
        Board board = boardService.deleteBoard(boardId,memberId);
        return ResultDataResponseDTO.of(board.getId());
    }

    @GetMapping(value = "/board/list/{categoryId}/{boardType}/{sortType}")
    public ResultDataResponseDTO<List<BoardDTO.BoardInfoDTO>> getBoardListByCategoryId(@PathVariable Long categoryId,@PathVariable BoardType boardType,@PathVariable SortType sortType){
        return ResultDataResponseDTO.of(boardService.getBoardListByCategoryId(categoryId,boardType,sortType));
    }

    @GetMapping(value = "/board/list/{memberId}/{boardType}/{sortType}")
    public ResultDataResponseDTO<List<BoardDTO.BoardInfoDTO>> getBoardListByMemberId(@PathVariable Long memberId,@PathVariable BoardType boardType,@PathVariable SortType sortType){
        return ResultDataResponseDTO.of(boardService.getBoardListByMemberId(memberId,boardType,sortType));
    }

    //게시글 조회
    @GetMapping(value = "/board/{boardId}")
    public ResultDataResponseDTO<BoardDTO.BoardInfoDTO> getBoardInfo(@PathVariable long boardId, @PathVariable long memberId){
        return ResultDataResponseDTO.of(boardService.getBoardInfo(boardId,memberId));
    }

    @GetMapping(value = "/board/list")
    public List<BoardDTO.BoardInfoDTO> getBoardInfoList(){
        return null;
    }

}
