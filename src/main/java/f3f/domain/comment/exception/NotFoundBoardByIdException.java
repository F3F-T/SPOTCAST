package f3f.domain.comment.exception;

public class NotFoundBoardByIdException extends IllegalArgumentException {
    public NotFoundBoardByIdException() {
        super("해당 아이디를 가진 포스트가 존재하지 않습니다.");
    }

    public NotFoundBoardByIdException(String message) {
        super(message);
    }
}