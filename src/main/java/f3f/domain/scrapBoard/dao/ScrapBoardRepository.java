package f3f.domain.scrapBoard.dao;

import f3f.domain.board.domain.Board;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ScrapBoardRepository extends JpaRepository<ScrapBoard, Long> {

    @Modifying
    @Query(value = "delete from scrap_board sb where sb.scrap_id = :scrap_id",nativeQuery = true)
    void deleteAllByScrapId(@Param("scrap_id") Long scrap_id);


    List<ScrapBoard> findByScrapId(Long scrapId);
}
