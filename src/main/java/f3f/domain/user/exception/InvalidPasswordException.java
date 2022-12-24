package f3f.domain.user.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String s) {
        super(s);
    }
}
