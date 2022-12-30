package f3f.domain.scrap.dao;

import f3f.domain.scrap.domain.ScrapBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapBoardRepository extends JpaRepository<ScrapBoard, Long> {

    void deleteByScrapId(Long scrapId);
}
