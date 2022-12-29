package f3f.domain.likes.exception;

public class ExistLikeAlreadyException extends IllegalArgumentException {
    public ExistLikeAlreadyException(){super("이미 좋아요를 눌렀습니다.");}
}
