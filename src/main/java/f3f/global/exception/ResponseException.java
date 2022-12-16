package f3f.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseException extends RuntimeException{
    private final ErrorCode errorCode;
}
