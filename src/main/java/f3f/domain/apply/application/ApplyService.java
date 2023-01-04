package f3f.domain.apply.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.publicModel.BoardType;
import f3f.domain.apply.dao.ApplyRepository;
import f3f.domain.apply.domain.Apply;
import f3f.domain.apply.exception.NotfoundException;
import f3f.domain.apply.exception.UnauthorizedMemberException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
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
    public void sendApply(Apply apply){
        Board board = boardRepository.findById(apply.getBoard().getId()).orElseThrow();
        if (!board.getBoardType().equals(BoardType.RECRUIT)){
            throw new IllegalArgumentException();
        }

        if(apply.getRecruiter().getId() != board.getMember().getId()){
            // 예외
        }

        applyRepository.save(apply);
    }

    //지원한 사람만 취소가 가능함
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
    public Apply getApply(long applyId , long memberId){
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotfoundException("존재하지 않는 지원"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotfoundException("존재하지 않는 유저"));

        if(apply.getVolunteer().getId() == member.getId() || apply.getRecruiter().getId() == member.getId()){
            return apply;
        }else{
            throw new UnauthorizedMemberException();
        }
    }

    @Transactional(readOnly = true)
    public List<Apply> getRecruiterApplyList(long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        return member.getRecruiterList();
    }

    @Transactional(readOnly = true)
    public List<Apply> getVolunteerApplyList(long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        return member.getVolunteerList();
    }
}
