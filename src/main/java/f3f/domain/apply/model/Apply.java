package f3f.domain.apply.model;


import f3f.domain.board.model.Board;
import f3f.domain.company.model.Company;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    private User volunteer;
}
