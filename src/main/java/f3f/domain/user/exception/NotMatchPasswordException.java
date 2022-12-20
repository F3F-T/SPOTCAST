package f3f.domain.user.exception;

public class NotMatchPasswordException extends IllegalArgumentException{
    public NotMatchPasswordException() {
    }

    public NotMatchPasswordException(String s) {
        super(s);
    }
}
