package f3f.domain.user.exception;

public class RefreshTokenNotFoundException extends IllegalArgumentException{
    public RefreshTokenNotFoundException() {
    }

    public RefreshTokenNotFoundException(String s) {
        super(s);
    }
}
