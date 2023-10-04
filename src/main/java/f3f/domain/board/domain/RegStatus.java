package f3f.domain.board.domain;

public enum RegStatus {

    ALL(1, "전체"),
    ONGOING(2,"진행 중"),
    END(3,"마감");

    private long id;
    private String type;

    RegStatus(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
