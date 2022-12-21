package f3f.domain.comment.exception;

public class NotFoundUserException extends IllegalArgumentException {

    public NotFoundUserException() {
        super("해당 아이디로 존재하는 유저가 없습니다.");
    }

    public NotFoundUserException(String s) {
        super(s);
    }
}
