package f3f.domain.portfolio.application;

import f3f.domain.portfolio.dao.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {


    private final PortfolioRepository portfolioRepository;


    public long savePortfolio(){
        return 0;
    }

    public long deletePortfolio(){
        return 0;
    }

    public long updatePortfolio(){
        return 0;
    }

    public long getPortfolioListByMemberId(){
        return 0;
    }
}
