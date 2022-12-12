package f3f.domain.model;

public enum LoginUserType {
    GENERAL_USER(1,"user"),ADMIN_USER(2,"admin"),COMPANY_USER(3,"company");

    private long id;
    private String name;

    LoginUserType(long id, String name) {
        this.id = id;
        this.name = name;
    }
}