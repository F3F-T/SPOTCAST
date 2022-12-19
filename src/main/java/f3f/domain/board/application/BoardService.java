package f3f.domain.board.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.exception.BoardMissMatchUserException;
import f3f.domain.board.exception.NotFoundBoardCategoryException;
import f3f.domain.board.exception.NotFoundBoardException;
import f3f.domain.board.exception.NotFoundBoardUserException;
import f3f.domain.category.dao.CategoryRepository;
import f3f.domain.category.domain.Category;
import f3f.domain.category.exception.NotFoundCategoryException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void saveBoard(BoardDTO.SaveRequest request) {

        if (request.getCategory() == null){
            throw new NotFoundBoardCategoryException("게시글에 카테고리 정보가 존재하지 않습니다.");
        }

        if (request.getMember() == null){
            throw new NotFoundBoardUserException("게시글에 유저 정보가 존재하지 않습니다.");
        }

        boardRepository.save(request.toEntity());
    }

    @Transactional
    public Board updateBoard(long boardId, long userId, BoardDTO.SaveRequest request) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));

        if (member.getId() != board.getMember().getId()){
            throw new BoardMissMatchUserException();
        }

        board.updateBoard(request);

        return request.toEntity();
    }

    @Transactional
    public Board deleteBoard(long boardId, long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));

        if (member.getId() != board.getMember().getId()){
            throw new BoardMissMatchUserException();
        }
        boardRepository.deleteById(board.getId());

        return board;
    }

    @Transactional(readOnly = true)
    public BoardInfoDTO getBoardInfo(long boardId , long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(NotFoundBoardException::new);

        if (member.getId() != board.getMember().getId()){
            throw new BoardMissMatchUserException();
        }
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
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundCategoryException::new);

        List<BoardInfoDTO> boardInfoList = category.getBoardList().stream()
                .map(Board::toBoardInfoDTO).collect(Collectors.toList());

        return boardInfoList;
    }
}
