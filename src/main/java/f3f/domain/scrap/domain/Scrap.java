package f3f.domain.scrap.domain;

import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "scrap", fetch = FetchType.LAZY)
    private List<ScrapBoard> scrapBoardList = new ArrayList<>();
}
