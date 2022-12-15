package f3f.domain.board.exception;

public class NotFoundBoardUserException extends IllegalArgumentException {

    public NotFoundBoardUserException() {
    }

    public NotFoundBoardUserException(String s) {
        super(s);
    }
}
