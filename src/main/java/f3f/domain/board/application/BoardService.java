package f3f.domain.board.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.dao.SearchBoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.domain.RegStatus;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.BoardListResponse;
import f3f.domain.board.exception.BoardMissMatchUserException;
import f3f.domain.board.exception.NotFoundBoardCategoryException;
import f3f.domain.board.exception.NotFoundBoardException;
import f3f.domain.category.dao.CategoryRepository;
import f3f.domain.comment.dao.CommentRepository;
import f3f.domain.likes.dao.LikesRepository;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * 9. 보드 아이디로 좋아요 조회--
 * 10. 보드 아이디로 댓글 조회 (자식댓글까지 전부다)--
 * 11. 이미지 업로드 기능 구현
 * 12. 구인글에 지원기능 테스트
 * 13. 본인이 지원한 구인글 리스트
 * 14. 본인이 작성한 구인글 리스트
 * 15. 이미지 저장 API 구현
 */
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final SearchBoardRepository searchBoardRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public Long saveBoard(BoardDTO.SaveRequest request) {

        if (request.getCategory() == null){
            throw new NotFoundBoardCategoryException("게시글에 카테고리 정보가 존재하지 않습니다.");
        }

        Member member = memberRepository.findById(request.getMember().getId()).orElseThrow(() -> new MemberNotFoundException());
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .boardType(request.getBoardType())
                .member(member)
                .supportEmail(request.getSupportEmail())
                .participationPeriod(request.getParticipationPeriod())
                .pay(request.getPay())
                .recruitType(request.getRecruitType())
                .recruitVolume(request.getRecruitVolume())
                .profitStatus(request.getProfitStatus())
                .regDate(request.getRegDate())
                .phone(request.getPhone())
                .category(request.getCategory())
                .member(member)
                .production(request.getProduction())
                .build();
        boardRepository.save(board);

        return board.getId();
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

        return board;
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
    public BoardInfoDTO getBoardInfo(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NotFoundBoardException::new);
        board.updateViewCount(board);
        return board.toBoardInfoDTO();
    }

    /*
     * 유저 식별자로 게시글 조회
     */
    @Transactional(readOnly = true)
    public Page<BoardListResponse> getBoardListByMemberId(Long memberId, String boardType, String profitStatus, Pageable pageable){
        Page<BoardListResponse> boardListByUserId = searchBoardRepository.getBoardListInfoByMemberId(memberId, boardType,profitStatus,pageable);
        for (BoardListResponse boardListResponse : boardListByUserId) {
            boardListResponse.setCommentCount(commentRepository.findByBoardId(boardListResponse.getId()).size());
            boardListResponse.setLikeCount(likesRepository.findByBoardId(boardListResponse.getId()).size());
        }
        return boardListByUserId;
    }
    /*
     * 카테고리 식별자로 게시글 조회
     */
    @Transactional(readOnly = true)
    public Page<BoardListResponse> getBoardListByCategoryId(String boardType, Long categoryId, String profitStatus, Pageable pageable, String isOngoing){
        Page<BoardListResponse> boardByCategoryId;
        if (categoryId == 0){
            boardByCategoryId = searchBoardRepository.getBoardList(boardType,profitStatus,pageable,isOngoing);
        }else{
            boardByCategoryId = searchBoardRepository.getBoardListInfoByCategoryId(boardType,categoryId,profitStatus,pageable,isOngoing);
        }
        for (BoardListResponse boardListResponse : boardByCategoryId) {
            boardListResponse.setCommentCount(commentRepository.findByBoardId(boardListResponse.getId()).size());
            boardListResponse.setLikeCount(likesRepository.findByBoardId(boardListResponse.getId()).size());
        }

        return boardByCategoryId;
    }
}
