package f3f.domain.portfolio.exception;

public class PortfolioNotFoundException extends IllegalArgumentException {

    public PortfolioNotFoundException() {
        super("해당 아이디로 존재하는 포트폴리오가 없습니다.");
    }

    public PortfolioNotFoundException(String s) {
        super(s);
    }
}


