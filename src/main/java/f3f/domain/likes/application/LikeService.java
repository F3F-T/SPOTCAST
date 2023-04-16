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

import java.util.*;

@RequiredArgsConstructor
@Service
public class LikeService extends BaseTimeEntity {

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addLike(long boardId, long memberId) {

        Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoardByIdException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);


        //중복 좋아요 방지
        if(isAlreadyLike(member, board)) {
           throw new ExistLikeAlreadyException();
        }

        //likesRepository.save(new Likes(member, board));

        Likes likes = Likes.builder()
                .member(member).board(board).build();
        board.getLikesList().add(likes);
        likesRepository.save(likes);

        return likes.getId();
    }
    @Transactional
    public Likes deleteLike(Long boardId, Long memberId) {
        Likes byMemberIdAndBoardId = likesRepository.findByMemberIdAndBoardId(memberId, boardId);
        likesRepository.deleteById(byMemberIdAndBoardId.getId());
        return  byMemberIdAndBoardId;
    }


    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isAlreadyLike(Member member, Board board) {
        return (!likesRepository.findByMemberAndBoard(member, board).isEmpty());
    }



    @Transactional(readOnly = true)
    public Map<String ,Object> getListListByBoardId(long boardId){
        //todo 쿼리 최적화 필요
        List<LikeDTO.LikeInfo> likeInfoList = new ArrayList<>();
        Map<String,Object> map = new LinkedHashMap<>();
        List<Likes> likeList = likesRepository.findByBoardId(boardId);
        for (Likes likes : likeList) {
            LikeDTO.LikeInfo likeInfo = LikeDTO.LikeInfo.builder()
                    .likeId(likes.getId())
                    .boardId(likes.getBoard().getId())
                    .memberId(likes.getMember().getId())
                    .build();
            likeInfoList.add(likeInfo);
        }
        map.put("likeCount",likeInfoList.size());
        map.put("likeDetailList",likeInfoList);
        return map;
    }


}
