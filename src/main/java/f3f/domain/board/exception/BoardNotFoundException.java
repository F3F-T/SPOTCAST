package f3f.domain.board.exception;

public class BoardNotFoundException extends IllegalArgumentException {
    public BoardNotFoundException() {
    }

    public BoardNotFoundException(String s) {
        super(s);
    }
}
