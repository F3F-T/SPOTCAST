package f3f.domain.bookmark.domain;

import f3f.domain.portfolio.domain.PortfolioBookmark;
import f3f.domain.user.domain.Member;
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
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id")
    private List<PortfolioBookmark> portfolioBookmarkList = new ArrayList<>();
}
