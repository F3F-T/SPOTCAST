package f3f.domain.model;

import lombok.Getter;

@Getter
public enum LoginMemberType {
    GENERAL_USER(1,"user"),ADMIN_USER(2,"admin"),COMPANY_USER(3,"company_user"),COMPANY(4,"company");

    private long id;
    private String name;

    LoginMemberType(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
