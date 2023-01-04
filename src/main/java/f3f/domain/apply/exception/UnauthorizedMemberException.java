package f3f.domain.apply.exception;

public class UnauthorizedMemberException extends IllegalArgumentException {

    public UnauthorizedMemberException() {
    }

    public UnauthorizedMemberException(String s) {
        super(s);
    }
}
