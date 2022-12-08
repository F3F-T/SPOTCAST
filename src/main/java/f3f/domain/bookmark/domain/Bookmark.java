package f3f.domain.bookmark.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.portfolio.domain.PortfolioBookmark;
import f3f.domain.scrap.domain.ScrapBoard;
import f3f.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bookmark {
    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id")
    private List<PortfolioBookmark> portfolioBookmarkList = new ArrayList<>();
}
