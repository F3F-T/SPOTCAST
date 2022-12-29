package f3f.domain.board.exception;

public class NotFoundBoardCategoryException extends IllegalArgumentException {

    public NotFoundBoardCategoryException() {
    }

    public NotFoundBoardCategoryException(String s) {
        super(s);
    }
}
