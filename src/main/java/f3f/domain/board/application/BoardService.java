package f3f.domain.board.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.exception.NotFoundBoardException;
import f3f.domain.board.exception.NotFoundBoardCategoryException;
import f3f.domain.board.exception.NotFoundBoardUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/*
 * 필요한 기능
 * 1. 게시글 저장
 * 2. 게시글 수정
 * 3. 게시글 삭제
 * 4. 게시글 조회
 * 5. 유저 식별자로 게시글 리스트 조회
 * 6. 카테고리 식별자로 게시글 리스트 조회
 * 7. 최신순으로 게시글 조회
 * 8. 조회수 많은순으로 게시글 조회
 */
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
                .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));

        board.updateBoard(request);

        return request.toEntity();
    }

    @Transactional
    public Board deleteBoard(long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));

        boardRepository.deleteById(board.getId());

        return board;
    }

    @Transactional(readOnly = true)
    public BoardInfoDTO getBoardInfo(long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(NotFoundBoardException::new);

        return board.toBoardInfoDTO();
    }

    /*
     * 유저 식별자로 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<BoardInfoDTO> getBoardListByUserId(long userId){
        return null;
    }

    /*
     * 카테고리 식별자로 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<BoardInfoDTO> getBoardListByCategoryId(long categoryId){
        return null;
    }
}
