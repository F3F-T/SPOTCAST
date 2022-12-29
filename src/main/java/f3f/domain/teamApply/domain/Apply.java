package f3f.domain.teamApply.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Apply extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "apply_id")
    private Long id;

    private String application;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member volunteer;
}
