package f3f.domain.board.exception;

public class BoardMissMatchUserException extends IllegalArgumentException {

    public BoardMissMatchUserException() {
    }

    public BoardMissMatchUserException(String s) {
        super(s);
    }
}
