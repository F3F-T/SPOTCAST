package f3f.domain.scrap.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.scrap.domain.ScrapBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapBoardRepository extends JpaRepository<ScrapBoard, Long> {

    void deleteByScrapId(Long scrapId);

    List<Board> findByScrapId(Long scrapId);
}
