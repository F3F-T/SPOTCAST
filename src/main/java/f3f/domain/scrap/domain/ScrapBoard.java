package f3f.domain.scrap.domain;

import f3f.domain.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ScrapBoard {

    @Id
    @GeneratedValue
    @Column(name = "scrapPost_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
