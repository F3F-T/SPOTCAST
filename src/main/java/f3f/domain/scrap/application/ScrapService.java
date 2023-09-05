package f3f.domain.scrap.application;

import f3f.domain.scrap.dao.ScrapRepository;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrapBoard.dao.ScrapBoardRepository;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static f3f.domain.scrap.dto.ScrapDTO.*;

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
    public Scrap saveScrapBox(SaveRequest request, Long memberId){

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));


        Scrap scrap = scrapRepository.save(request.toEntity(findMember));
        return scrap;
    }

    /**
     * 스크랩 박스 이름 수정
     * @param request
     * @param memberId
     */
    @Transactional
    public void updateScrapBox(UpdateRequest request, Long memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        Scrap scrap = scrapRepository.findById(request.getScrapId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX,"존재하지 않는 스크랩박스입니다."));

        scrap.updateScrap(request);
    }

    /**
     * 스크랩 박스 삭제
     * @param deleteRequest
     * @param memberId
     */
    @Transactional
    public void deleteScrapBox(ScrapDeleteRequestDTO deleteRequest, Long memberId){

        Long scrapId = deleteRequest.getScrapId();
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX,"존재하지 않는 스크랩박스입니다."));

        if(!scrap.getMember().getId().equals(findMember.getId())){
            throw new GeneralException(ErrorCode.NOTCURRENT_MEMBER,"스크랩과 유저 정보가 일치하지 않습니다.");
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
    public List<ScrapInfoDTO> getScrapBoxList(Long memberId){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        List<ScrapInfoDTO> scrapList = findMember.getScrapList().stream()
                .map(scrap -> ScrapInfoDTO.of(scrap)).collect(Collectors.toList());

        return scrapList;

    }
}
