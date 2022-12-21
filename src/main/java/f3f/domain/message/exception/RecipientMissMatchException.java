package f3f.domain.message.exception;

public class RecipientMissMatchException extends IllegalArgumentException {

    public RecipientMissMatchException() {
    }

    public RecipientMissMatchException(String s) {
        super(s);
    }
}
