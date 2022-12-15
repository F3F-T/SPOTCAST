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
public class MemberBase extends BaseTimeEntity {

    @Id@GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String name;
    protected String password;

    @Enumerated(value = EnumType.STRING)
    private LoginMemberType loginMemberType;

    @Enumerated(value = EnumType.STRING)
    private LoginType loginType;

    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;


    protected String information;

}