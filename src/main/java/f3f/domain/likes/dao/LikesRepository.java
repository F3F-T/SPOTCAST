package f3f.domain.likes.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.likes.domain.Likes;
import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndBoard(Member member, Board board);

    Likes findByMemberIdAndBoardId(Long member_id, Long board_id);

    List<Likes> findByBoardId(long boardId);
}
