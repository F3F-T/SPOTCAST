package f3f.domain.user.exception;

public class NotGeneralLoginType extends IllegalArgumentException {
    public NotGeneralLoginType() {
    }

    public NotGeneralLoginType(String s) {
        super(s);
    }
}
