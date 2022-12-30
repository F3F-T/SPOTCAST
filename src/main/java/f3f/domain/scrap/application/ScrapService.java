package f3f.domain.scrap.application;

import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.scrap.dao.ScrapBoardRepository;
import f3f.domain.scrap.dao.ScrapRepository;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrap.exception.DuplicateScrapNameException;
import f3f.domain.scrap.exception.ScrapMissMatchUserException;
import f3f.domain.scrap.exception.ScrapNotFoundException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 필요 기능들
 * 1. 스크랩 박스 리스트 조회
 * 2. 스크랩 박스 삭제
 * 3. 스크랩 박스 생성
 * 4. 스크랩 박스 이름 수정
 */
@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;

    private final MemberRepository memberRepository;

    private final ScrapBoardRepository scrapBoardRepository;


    /**
     * 스크랩 박스 생성
     * @param request
     */
    @Transactional(readOnly = true)
    public void saveScrapBox(ScrapDTO.SaveRequest request){

        Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        boolean nameExists = findMember.getScrapList().contains(request.getName());
        if(nameExists){
            throw new DuplicateScrapNameException("스크랩 이름이 중복되었습니다.");
        }

        scrapRepository.save(request.toEntity(findMember));
    }

    /**
     * 스크랩 박스 이름 수정
     * @param request
     * @param scrapId
     */
    public void updateScrapBox(ScrapDTO.SaveRequest request,Long scrapId) {

        Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        boolean nameExists = findMember.getScrapList().contains(request.getName());
        if(nameExists){
            throw new DuplicateScrapNameException("스크랩 이름이 중복되었습니다.");
        }

        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapNotFoundException("존재하지 않는 스크랩입니다."));

        scrap.updateScrap(request);
    }

    /**
     * 스크랩 박스 삭제
     * @param scrapId
     * @param memberId
     */
    public void deleteScrapBox(Long scrapId, Long memberId){

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapNotFoundException("존재하지 않는 스크랩입니다."));

        if(scrap.getMember().getId()!= findMember.getId()){
            throw new ScrapMissMatchUserException();
        }

        scrapRepository.deleteById(scrapId);
        scrapBoardRepository.deleteByScrapId(scrapId);

    }

    /**
     * 스크랩 박스 리스트 조회
     * @param memberId
     * @return
     */
    public List<ScrapDTO.ScrapInfoDTO> getScrapBoxList(Long memberId){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        List<ScrapDTO.ScrapInfoDTO> scrapList = findMember.getScrapList().stream()
                .map(Scrap::toScrapInfoDTO).collect(Collectors.toList());

        return scrapList;

    }
}
