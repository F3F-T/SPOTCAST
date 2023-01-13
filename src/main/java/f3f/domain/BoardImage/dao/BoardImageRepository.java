package f3f.domain.BoardImage.dao;

import f3f.domain.BoardImage.domain.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {
}
