package f3f.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UserBase extends BaseTimeEntity {

    @Id@GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private  LoginType loginType;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private String information;

}
