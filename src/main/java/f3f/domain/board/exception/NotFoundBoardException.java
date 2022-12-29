package f3f.domain.board.exception;

public class NotFoundBoardException extends IllegalArgumentException {
    public NotFoundBoardException() {
    }

    public NotFoundBoardException(String s) {
        super(s);
    }
}
