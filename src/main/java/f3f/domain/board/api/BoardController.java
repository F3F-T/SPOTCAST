package f3f.domain.board.api;


import f3f.domain.board.application.BoardService;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/board")
    public String saveBoard(@RequestBody BoardDTO.SaveRequest request){
        boardService.saveBoard(request);
        
        return "OK";
    }

    @PostMapping(value = "/board/{boardId]")
    public BoardDTO.BoardInfoDTO updateBoard(@PathVariable long boardId, @RequestBody BoardDTO.SaveRequest request){
        Board board = boardService.updateBoard(boardId, request);
        return board.toBoardInfoDTO();
    }

    @PostMapping(value = "/board/{boardId}")
    public long deleteBoard(@PathVariable long boardId){
        Board board = boardService.deleteBoard(boardId);

        return board.getId();
    }

    @PostMapping(value = "/board/{boardId}}")
    public BoardDTO.BoardInfoDTO getBoardInfo(@PathVariable long boardId){
        return boardService.getBoardInfo(boardId);
    }
}
