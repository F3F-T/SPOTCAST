package f3f.global.exception;

import f3f.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseException extends RuntimeException{
    private final ErrorCode errorCode;
}
