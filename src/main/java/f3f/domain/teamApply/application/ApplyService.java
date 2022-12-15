package f3f.domain.teamApply.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.model.BoardType;
import f3f.domain.teamApply.dao.ApplyRepository;
import f3f.domain.teamApply.domain.Apply;
import f3f.domain.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void sendApply(Apply apply){
        Board board = boardRepository.findById(apply.getBoard().getId()).orElseThrow();
        if (!board.getBoardType().equals(BoardType.RECRUIT)){
            throw new IllegalArgumentException();
        }
        if(apply.getRecruiter().getId() == board.getMember().getId()){
            //구인자가 게시글을 쓴사람인지 체크
        }

        applyRepository.save(apply);
    }

    public void cancelApply(){

    }
    public void getApply(){

    }
    public void getRecruiterApplyList(){

    }

    public void getVolunteerApplyList(){

    }
}
