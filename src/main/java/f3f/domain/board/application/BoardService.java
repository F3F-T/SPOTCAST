package f3f.domain.board.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.exception.BoardNotFoundException;
import f3f.domain.board.exception.NotFoundBoardCategoryException;
import f3f.domain.board.exception.NotFoundBoardUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void saveBoard(BoardDTO.SaveRequest request) {

        if (request.getCategory() == null){
            throw new NotFoundBoardCategoryException("게시글에 카테고리 정보가 존재하지 않습니다.");
        }

        if (request.getUser() == null){
            throw new NotFoundBoardUserException("게시글에 유저 정보가 존재하지 않습니다.");
        }

        boardRepository.save(request.toEntity());
    }

    @Transactional
    public Board updateBoard(long boardId, BoardDTO.SaveRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));

        board.updateBoard(request);

        return request.toEntity();
    }

    @Transactional
    public Board deleteBoard(long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));

        boardRepository.deleteById(board.getId());

        return board;
    }

    @Transactional(readOnly = true)
    public BoardDTO.BoardInfoDTO getBoardInfo(long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        return board.toBoardInfoDTO();
    }
}
