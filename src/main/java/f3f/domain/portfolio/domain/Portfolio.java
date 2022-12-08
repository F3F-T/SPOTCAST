package f3f.domain.portfolio.domain;


import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "portfolio",fetch = FetchType.LAZY)
    private List<PortfolioBookmark> bookmarkList = new ArrayList<>();

}
