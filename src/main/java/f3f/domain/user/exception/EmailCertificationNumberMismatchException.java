package f3f.domain.user.exception;

public class EmailCertificationNumberMismatchException extends IllegalArgumentException{
    public EmailCertificationNumberMismatchException() {
    }

    public EmailCertificationNumberMismatchException(String s) {
        super(s);
    }
}
