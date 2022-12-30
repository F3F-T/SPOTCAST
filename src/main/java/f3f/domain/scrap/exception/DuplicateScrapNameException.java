package f3f.domain.scrap.exception;

public class DuplicateScrapNameException extends IllegalArgumentException{
    public DuplicateScrapNameException() {
    }

    public DuplicateScrapNameException(String s) {
        super(s);
    }
}
