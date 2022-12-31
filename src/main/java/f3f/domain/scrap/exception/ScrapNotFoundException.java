package f3f.domain.scrap.exception;

public class ScrapNotFoundException extends IllegalArgumentException{
    public ScrapNotFoundException() {
    }

    public ScrapNotFoundException(String s) {
        super(s);
    }
}
