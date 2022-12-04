package f3f.domain.company.model;

import f3f.domain.apply.model.Apply;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.model.LoginBase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Company  extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    private String name;

    @Embedded
    private LoginBase loginBase;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company" ,fetch = FetchType.LAZY)
    private List<Apply> applyList = new ArrayList<>();
}
