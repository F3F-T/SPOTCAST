package f3f.domain.portfolio.api;

import f3f.domain.portfolio.application.PortfolioService;
import f3f.domain.portfolio.dao.PortfolioRepository;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.portfolio.dto.PortfolioDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PortfolioController {


    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;

    @PostMapping(value = "/portfolio")
    public List<PortfolioDTO.SaveRequest> savePortfolioList(List<PortfolioDTO.SaveRequest>request){

        for (PortfolioDTO.SaveRequest saveRequest : request) {
            portfolioService.savePortfolio(saveRequest);
        }
        return request;
    }

    @PostMapping(value = "/portfolio/update")
    public List<PortfolioDTO.SaveRequest> updatePortfolio (List<PortfolioDTO.SaveRequest>request){

        Long memberId = SecurityUtil.getCurrentMemberId();
        List<Portfolio> portfolioRepositoryByMemberId = portfolioRepository.findByMemberId(memberId);
        for (Portfolio portfolio : portfolioRepositoryByMemberId) {
            portfolioService.deletePortfolio(portfolio.getId(),memberId);
        }

        for (PortfolioDTO.SaveRequest saveRequest : request) {
            portfolioService.savePortfolio(saveRequest);
        }
        return request;
    }
}
