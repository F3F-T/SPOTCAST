package f3f.domain.user.exception;

public class InvalidAccessTokenException extends IllegalArgumentException{
    public InvalidAccessTokenException() {
    }

    public InvalidAccessTokenException(String s) {
        super(s);
    }
}
