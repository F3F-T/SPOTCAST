package f3f.domain.portfolio.dao;

import f3f.domain.portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {


    List<Portfolio> findByMemberId(long memberId);
}
