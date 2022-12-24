package f3f.domain.user.exception;

public class NotGeneralLoginTypeException extends IllegalArgumentException {
    public NotGeneralLoginTypeException() {
    }

    public NotGeneralLoginTypeException(String s) {
        super(s);
    }
}
