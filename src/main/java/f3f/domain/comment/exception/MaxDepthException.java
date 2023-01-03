package f3f.domain.comment.exception;

public class MaxDepthException extends IllegalArgumentException {

    public MaxDepthException() { super("depth를 초과했습니다."); }

    public MaxDepthException(String message) {
        super(message);
    }
}
