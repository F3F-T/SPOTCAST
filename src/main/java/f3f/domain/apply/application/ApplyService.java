package f3f.domain.apply.application;

import f3f.domain.apply.dao.ApplyRepository;
import f3f.domain.apply.domain.Apply;
import f3f.domain.apply.dto.ApplyDTO;
import f3f.domain.apply.exception.NotfoundException;
import f3f.domain.apply.exception.UnauthorizedMemberException;
import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.publicModel.BoardType;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO.MemberBoardInfoResponseDto;
import f3f.domain.user.exception.MemberNotFoundException;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendApply(ApplyDTO.SaveRequest request){
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow();
        if (!board.getBoardType().equals(BoardType.RECRUIT)){
            throw new GeneralException(ErrorCode.VALIDATION_ERROR ,"구인글 아님");
        }

        Member recruiter = memberRepository.findById(request.getRecruiterId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 유저 정보입니다."));
        Member volunteer = memberRepository.findById(request.getVolunteerId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER, "지원자 정보가 존재하지 않습니다."));

        Apply apply = Apply.builder()
                .recruiter(recruiter)
                .volunteer(volunteer)
                .board(board)
                .build();

        applyRepository.save(apply);
    }

    //지원한 사람만 취소가 가능함
    //리턴타입수정
    @Transactional
    public Apply cancelApply(long applyId, long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotfoundException());

        if (member.getId() != apply.getVolunteer().getId()){
            throw new UnauthorizedMemberException();
        }

        applyRepository.deleteById(apply.getId());
        return apply;
    }

    @Transactional(readOnly = true)
    public ApplyDTO.ApplyInfo getApply(long applyId , long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotfoundException("존재하지 않는 유저"));

        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotfoundException("존재하지 않는 지원"));

        MemberBoardInfoResponseDto recruiter = apply.getRecruiter().toBoardMemberDTO();
        MemberBoardInfoResponseDto volunteer = apply.getVolunteer().toBoardMemberDTO();
        BoardDTO.BoardInfoDTO boardInfoDTO = apply.getBoard().toBoardInfoDTO();
        if(apply.getVolunteer().getId() == member.getId() || apply.getRecruiter().getId() == member.getId()){
            ApplyDTO.ApplyInfo applyInfo = ApplyDTO.ApplyInfo.builder()
                    .applyId(apply.getId())
                    .volunteer(volunteer)
                    .recruiter(recruiter)
                    .board(boardInfoDTO)
                    .build();
            return applyInfo;
        }else{
            throw new UnauthorizedMemberException();
        }
    }

    //리턴타입 수정
    @Transactional(readOnly = true)
    //public List<ApplyDTO.ApplyInfo> getRecruiterApplyList(long recruiterId){
        public List<Apply> getRecruiterApplyList(long recruiterId){
        Member member = memberRepository.findById(recruiterId)
                .orElseThrow(() -> new MemberNotFoundException());
        List<Apply> applyInfoList = applyRepository.findByRecruiterId(member.getId());
        return applyInfoList;
    }

    //리턴타입 수정
    @Transactional(readOnly = true)
    public List<Apply> getVolunteerApplyList(long volunteerId){
        //public List<ApplyDTO.ApplyInfo> getVolunteerApplyList(long volunteerId){
        Member member = memberRepository.findById(volunteerId)
                .orElseThrow(() -> new MemberNotFoundException());
        List<Apply> applyInfoList = applyRepository.findByVolunteerId(member.getId());
        return applyInfoList;
    }
}
