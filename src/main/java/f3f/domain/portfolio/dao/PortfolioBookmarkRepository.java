package f3f.domain.portfolio.dao;

import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.portfolio.domain.PortfolioBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioBookmarkRepository extends JpaRepository<PortfolioBookmark, Long> {

    void deleteByBookmarkId(Long bookmarkId);

    List<Portfolio> findByBookmarkId(Long bookmarkId);

    Optional<PortfolioBookmark> findByBookmarkIdAndPortfolioId(Long bookmarkId, Long PortfolioId);

}
