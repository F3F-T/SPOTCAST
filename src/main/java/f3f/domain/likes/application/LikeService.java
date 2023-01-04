package f3f.domain.likes.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.comment.exception.NotFoundBoardByIdException;
import f3f.domain.likes.dao.LikesRepository;
import f3f.domain.likes.domain.Likes;
import f3f.domain.likes.dto.LikeDTO;
import f3f.domain.likes.exception.ExistLikeAlreadyException;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService extends BaseTimeEntity {

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addLike(LikeDTO.SaveRequest saveRequest) {

        Board board = boardRepository.findById(saveRequest.getBoard().getId()).orElseThrow(NotFoundBoardByIdException::new);
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(saveRequest.getMember().getId()).orElseThrow(MemberNotFoundException::new));


        //중복 좋아요 방지
        if(isAlreadyLike(member, board)) {
           throw new ExistLikeAlreadyException();
        }

        //likesRepository.save(new Likes(member, board));

        Likes likes = saveRequest.toEntity();
        board.getLikesList().add(likes);
        likesRepository.save(likes);

        return likes.getId();
    }


    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isAlreadyLike(Optional<Member> member, Board board) {
        return !(likesRepository.findByMemberIdAndBoardId(member, board).isEmpty());
    }

}
