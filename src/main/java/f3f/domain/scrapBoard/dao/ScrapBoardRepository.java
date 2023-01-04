package f3f.domain.scrapBoard.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapBoardRepository extends JpaRepository<ScrapBoard, Long> {

    void deleteAllByScrapId(Long scrapId);


    List<ScrapBoard> findByScrapId(Long scrapId);
}
