package f3f.domain.user.exception;

public class UnauthenticatedUserException extends RuntimeException{

    public UnauthenticatedUserException(String message) {
        super(message);
    }
}