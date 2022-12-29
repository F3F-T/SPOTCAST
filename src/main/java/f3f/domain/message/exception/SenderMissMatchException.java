package f3f.domain.message.exception;

public class SenderMissMatchException extends IllegalArgumentException {
    public SenderMissMatchException() {
    }

    public SenderMissMatchException(String s) {
        super(s);
    }
}
