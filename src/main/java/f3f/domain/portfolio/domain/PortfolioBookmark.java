package f3f.domain.portfolio.domain;

import f3f.domain.bookmark.domain.Bookmark;

import javax.persistence.*;

@Entity
public class PortfolioBookmark {

    @Id
    @GeneratedValue
    @Column(name = "portfolioBookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;
}
