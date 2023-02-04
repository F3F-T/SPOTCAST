package f3f.domain.board.domain;

public enum ProfitStatus {

    NON_PROFITABLE(1, "비수익성"),
    PROFITABLE(2,"수익");

    private long id;
    private String type;

    ProfitStatus(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
