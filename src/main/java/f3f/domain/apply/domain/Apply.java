package f3f.domain.apply.domain;


import f3f.domain.board.domain.Board;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @JoinColumn(name = "recruiter_id", updatable = false, referencedColumnName = "member_id")
    private Member recruiter;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "volunteer_id", updatable = false, referencedColumnName = "member_id")
    private Member volunteer;

    @Builder
    public Apply(String application, Board board, Member recruiter, Member volunteer) {
        this.application = application;
        this.board = board;
        this.recruiter = recruiter;
        this.volunteer = volunteer;
    }
}
