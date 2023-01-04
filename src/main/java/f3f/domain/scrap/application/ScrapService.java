package f3f.domain.scrap.application;

import f3f.domain.scrap.dao.ScrapRepository;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrap.exception.ScrapMissMatchMemberException;
import f3f.domain.scrap.exception.ScrapNotFoundException;
import f3f.domain.scrapBoard.dao.ScrapBoardRepository;
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
    @Transactional
    public Scrap saveScrapBox(ScrapDTO.SaveRequest request,Long memberId){

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));


        Scrap scrap = scrapRepository.save(request.toEntity(findMember));
        return scrap;
    }

    /**
     * 스크랩 박스 이름 수정
     * @param request
     * @param memberId
     */
    @Transactional
    public void updateScrapBox(ScrapDTO.UpdateRequest request,Long memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Scrap scrap = scrapRepository.findById(request.getScrapId())
                .orElseThrow(() -> new ScrapNotFoundException("존재하지 않는 스크랩입니다."));

        scrap.updateScrap(request);
    }

    /**
     * 스크랩 박스 삭제
     * @param deleteRequest
     * @param memberId
     */
    @Transactional
    public void deleteScrapBox(ScrapDTO.ScrapDeleteRequestDTO deleteRequest, Long memberId){

        Long scrapId = deleteRequest.getScrapId();
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapNotFoundException("존재하지 않는 스크랩박스입니다."));

        if(scrap.getMember().getId()!= findMember.getId()){
            throw new ScrapMissMatchMemberException();
        }

        scrapBoardRepository.deleteAllByScrapId(scrapId);
        scrapRepository.deleteById(scrapId);
    }

    /**
     * 스크랩 박스 리스트 조회
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ScrapDTO.ScrapInfoDTO> getScrapBoxList(Long memberId){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        List<ScrapDTO.ScrapInfoDTO> scrapList = findMember.getScrapList().stream()
                .map(Scrap::toScrapInfoDTO).collect(Collectors.toList());

        return scrapList;

    }
}
