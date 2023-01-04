package f3f.domain.bookmark.domain;

import f3f.domain.portfolioBookmark.domain.PortfolioBookmark;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy="bookmark", fetch = FetchType.LAZY)
    private List<PortfolioBookmark> portfolioBookmarkList = new ArrayList<>();

    @Builder
    public Bookmark( Long id, Member member){
        this.id = id;
        this.member = member;
    }
}
