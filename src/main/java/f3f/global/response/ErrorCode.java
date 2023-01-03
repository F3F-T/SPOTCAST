package f3f.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    OK(200, HttpStatus.OK, "Ok"),

    BAD_REQUEST(601, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(602, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND(603, HttpStatus.NOT_FOUND, "Requested resource is not found"),

    INTERNAL_ERROR(604, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(605, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),

    UNAUTHORIZED(606, HttpStatus.UNAUTHORIZED, "User unauthorized"),
    //이메일 중복
    DUPLICATION_EMAIL(630,HttpStatus.BAD_REQUEST,"이메일 중복"),

    //auth 불일치
    UNAUTHORIZATION(631,HttpStatus.UNAUTHORIZED,"authority 불일치"),
    //토큰 없음
    JWT_ACCESSDENIED(632,HttpStatus.UNAUTHORIZED,"토큰 없음");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return ErrorCode.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return ErrorCode.INTERNAL_ERROR;
                    } else {
                        return ErrorCode.OK;
                    }
                });
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
