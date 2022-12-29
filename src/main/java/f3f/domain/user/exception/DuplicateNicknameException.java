package f3f.domain.user.exception;

public class DuplicateNicknameException extends IllegalArgumentException {

    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException() {
    }

}
