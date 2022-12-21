package f3f.domain.comment.exception;

public class NotFoundParentException extends IllegalArgumentException{

    public NotFoundParentException() {
        super("parent comment가 존재하지 않습니다.");
    }

    public NotFoundParentException(String message) {
        super(message);
    }
}
