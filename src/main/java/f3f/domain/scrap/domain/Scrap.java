package f3f.domain.scrap.domain;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
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

    @Builder
    public Scrap(Long id, String name, Member member) {
        this.id = id;
        this.name = name;
        this.member = member;
    }

    public void updateScrap(ScrapDTO.SaveRequest request){
        this.name = request.getName();
    }

    public ScrapDTO.ScrapInfoDTO toScrapInfoDTO(){
        return ScrapDTO.ScrapInfoDTO.builder()
                .scrapId(this.id)
                .name(this.name)
                .build();
    }

}
