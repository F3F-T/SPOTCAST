package f3f.domain.publicModel;

import lombok.Getter;

@Getter
public enum LoginMemberType {
    GENERAL_USER(1,"general_user"),ADMIN_USER(2,"admin_user"),COMPANY_USER(3,"company_user"),COMPANY(4,"company");

    private long id;
    private String name;

    LoginMemberType(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
