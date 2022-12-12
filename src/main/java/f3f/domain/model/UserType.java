package f3f.domain.model;

public enum UserType {

    USER(1,"user"),ADMIN(2,"admin"),COMPANY(3,"company");

    private long id;
    private String type;

    UserType(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
