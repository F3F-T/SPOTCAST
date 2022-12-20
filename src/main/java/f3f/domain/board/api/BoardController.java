package f3f.domain.board.api;


import f3f.domain.board.application.BoardService;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/board")
    public String saveBoard(@RequestBody BoardDTO.SaveRequest request){
        boardService.saveBoard(request);
        
        return "OK";
    }

    @PutMapping(value = "/board/{boardId}/{memberId}")
    public BoardDTO.BoardInfoDTO updateBoard(@PathVariable long boardId, @PathVariable long memberId, @RequestBody BoardDTO.SaveRequest request){
        Board board = boardService.updateBoard(boardId, memberId,request);
        return board.toBoardInfoDTO();
    }

    @DeleteMapping(value = "/board/{boardId}/{memberId}")
    public long deleteBoard(@PathVariable long boardId, @PathVariable long memberId){
        Board board = boardService.deleteBoard(boardId,memberId);

        return board.getId();
    }


    @GetMapping(value = "/board/{boardId}/{memberId}")
    public BoardDTO.BoardInfoDTO getBoardInfo(@PathVariable long boardId, @PathVariable long memberId){
        return boardService.getBoardInfo(boardId,memberId);
    }
}
