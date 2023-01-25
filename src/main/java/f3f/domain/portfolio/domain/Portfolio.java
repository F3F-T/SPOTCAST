package f3f.domain.portfolio.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;


    private int orders;

    @Builder
    public Portfolio(Long id, Board board, Member member, int orders) {
        this.id = id;
        this.board = board;
        this.member = member;
        this.orders = orders;
    }
}
