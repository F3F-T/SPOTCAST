package f3f.domain.message.exception;

public class MessageNotFoundException extends IllegalArgumentException {
    public MessageNotFoundException() {
    }

    public MessageNotFoundException(String s) {
        super(s);
    }
}
