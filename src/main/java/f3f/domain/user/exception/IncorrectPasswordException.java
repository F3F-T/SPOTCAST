package f3f.domain.user.exception;

public class IncorrectPasswordException extends IllegalArgumentException{
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String s) {
        super(s);
    }
}
