package f3f.domain.likes.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.user.domain.Member;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
