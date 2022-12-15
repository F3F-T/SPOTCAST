package f3f.domain.user.exception;

public class UserNotFoundException extends IllegalArgumentException{

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
