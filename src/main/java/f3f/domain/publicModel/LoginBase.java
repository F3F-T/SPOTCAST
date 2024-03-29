package f3f.domain.publicModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginBase {

    protected String email;


    protected String password;

    protected LoginMemberType loginMemberType;
}
