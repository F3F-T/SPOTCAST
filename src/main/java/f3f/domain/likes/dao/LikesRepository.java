package f3f.domain.likes.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.likes.domain.Likes;
import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberIdAndBoardId(Optional<Member> member, Board board);
}
