package f3f.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //이메일 중복
    DUPLICATION_EMAIL(HttpStatus.BAD_REQUEST,"이메일 중복"),

    //auth 불일치
    UNAUTHORIZATION(HttpStatus.UNAUTHORIZED,"authority 불일치"),
    //토큰 없음
    JWT_ACCESSDENIED(HttpStatus.UNAUTHORIZED,"토큰 없음");

    private final HttpStatus httpStatus;
    private final String message;
}
