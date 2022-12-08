package f3f.domain.portfolio.domain;


import f3f.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
