package f3f.domain.portfolioBookmark.domain;

import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.portfolio.domain.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PortfolioBookmark {

    @Id
    @GeneratedValue
    @Column(name = "portfolioBookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @Builder
    public PortfolioBookmark(Long id, Bookmark bookmark, Portfolio portfolio){
        this.id = id;
        this.bookmark = bookmark;
        this.portfolio = portfolio;
    }

}
