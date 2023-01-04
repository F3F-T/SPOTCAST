package f3f.domain.publicModel;

public enum BoardType {

    RECRUIT(1,"구인"),
    JOB_SEARCH(2,"구직"),
    GENERAL(3,"일반");

    private long id;
    private String type;

    BoardType(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
