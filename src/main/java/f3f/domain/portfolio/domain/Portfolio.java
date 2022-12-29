package f3f.domain.portfolio.domain;


import f3f.domain.user.domain.Member;
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
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "portfolio",fetch = FetchType.LAZY)
    private List<PortfolioBookmark> bookmarkList = new ArrayList<>();

}
