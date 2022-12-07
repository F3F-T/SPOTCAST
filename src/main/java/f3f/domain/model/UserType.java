package f3f.domain.model;

public enum UserType {

    USER(1,"user"),COMPANY(2,"company");

    private long id;
    private String type;

    UserType(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
