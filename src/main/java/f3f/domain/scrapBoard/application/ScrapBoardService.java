package f3f.domain.scrapBoard.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.scrap.dao.ScrapRepository;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.scrap.exception.ScrapBoardMissMatchMemberException;
import f3f.domain.scrap.exception.ScrapNotFoundException;
import f3f.domain.scrapBoard.dao.ScrapBoardRepository;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import f3f.domain.scrapBoard.dto.ScrapBoardDTO;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 필요 기능들
 * 1. 스크랩 목록 조회
 * 2. 스크랩 추가
 * 3. 스크랩 삭제
 */
@Service
@RequiredArgsConstructor
public class ScrapBoardService {

    private final ScrapRepository scrapRepository;

    private final MemberRepository memberRepository;

    private final ScrapBoardRepository scrapBoardRepository;

    private final BoardRepository boardRepository;

    /**
     * 스크랩 추가
     *
     * @param memberId
     * @param scrapId
     * @param saveRequest
     */
    @Transactional
    public ScrapBoard saveScrap(Long memberId, Long scrapId,ScrapBoardDTO.SaveRequest saveRequest) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER));

        Board board = boardRepository.findById(saveRequest.getBoardId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_BOARD));

        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX));

        ScrapBoard scrapBoard = ScrapBoard.builder()
                .scrap(scrap)
                .board(board)
                .build();

        return scrapBoardRepository.save(scrapBoard);
    }

    /**
     * 스크랩 삭제
     * @param memberId
     * @param scrapId
     * @param deleteRequest
     */
    @Transactional
    public void deleteScrap(Long memberId, Long scrapId, ScrapBoardDTO.DeleteRequest deleteRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER));


        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX));

        if(scrap.getMember().getId()!= findMember.getId()){
            throw new ScrapBoardMissMatchMemberException();
        }

        scrapBoardRepository.deleteById(deleteRequest.getId());
    }

    /**
     * 스크랩 목록 조회
     * @param scrapId
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public List<BoardDTO.BoardInfoDTO> getScrapList(Long scrapId, Long memberId){
        memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER));

        scrapRepository.findById(scrapId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX));

        List<ScrapBoard> scrapList = scrapBoardRepository.findByScrapId(scrapId);

        List<BoardDTO.BoardInfoDTO> scrapBoardInfoList = scrapList.stream()
                .map(ScrapBoard::toBoardInfoDTO).collect(Collectors.toList());
        return scrapBoardInfoList;
    }
}
