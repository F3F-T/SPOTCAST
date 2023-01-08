package f3f.domain.portfolio.dao;

import f3f.domain.portfolio.dto.PortfolioDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomPortfolioRepository {
    List<PortfolioDTO.PortfolioInfo> getPortfolioListByMemberId(long memberId);
}
