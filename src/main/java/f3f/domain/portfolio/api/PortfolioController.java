package f3f.domain.portfolio.api;

import f3f.domain.portfolio.application.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
}
