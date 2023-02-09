package f3f.domain.BoardImage.dao;

import f3f.domain.BoardImage.domain.BoardImage;
import f3f.domain.BoardImage.dto.BoardImageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {

    List<BoardImageDTO.BoardImageInfo> findBoardImagesByBoardIdOrderByCreatedDateDesc(long boardId);
}
