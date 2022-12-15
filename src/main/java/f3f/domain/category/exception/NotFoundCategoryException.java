package f3f.domain.category.exception;

public class NotFoundCategoryException extends IllegalArgumentException {

    public NotFoundCategoryException() {
    }

    public NotFoundCategoryException(String s) {
        super(s);
    }
}
