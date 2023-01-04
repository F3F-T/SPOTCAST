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


    //요청한 정보가 유효하지 않음
    INVALID_EMAIL_REQUEST(400,HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."),

    INVALID_PASSWORD_REQUEST(400,HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    INVALID_EMAIL_AND_PASSWORD_REQUEST(400,HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."),


    //refresh token 유효하지 않음
    INVALID_REFRESHTOKEN(400,HttpStatus.BAD_REQUEST, "로그아웃 된 사용자입니다."),

    //현재 멤버와 불일치
    NOTCURRENT_MEMBER(400,HttpStatus.BAD_REQUEST,"사용자 정보가 일치하지 않습니다."),

    //이메일 인증 불일치
    EMAIL_CERTIFICATION_MISMATCH(400,HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),


    //멤버 없음
    NOTFOUND_MEMBER(400,HttpStatus.BAD_REQUEST,"존재하지 않는 사용자입니다."),

    //게시글 없음
    NOTFOUND_BOARD(400,HttpStatus.BAD_REQUEST,"존재하지 않는 게시글입니다."),

    //스크랩 없음
    NOTFOUND_SCRAP(400,HttpStatus.BAD_REQUEST,"존재하지 않는 스크랩입니다."),

    //중복 회원가입
    DUPLICATION_SIGNUP(400,HttpStatus.BAD_REQUEST,"중복 회원가입입니다. "),

    //이메일 중복
    DUPLICATION_EMAIL(400,HttpStatus.BAD_REQUEST,"이미 가입되어 있는 이메일입니다."),

    //authority 불일치
    AUTHORITY_FORBIDDEN(403,HttpStatus.FORBIDDEN,"접근 권한이 없습니다."),
    //토큰 없음
    JWT_ACCESS_DENIED(401,HttpStatus.UNAUTHORIZED,"유효하지 않은 토큰입니다.");

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
