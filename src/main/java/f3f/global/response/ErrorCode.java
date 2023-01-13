package f3f.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
public enum ErrorCode {

    OK(200, HttpStatus.OK),

    BAD_REQUEST(601, HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(602, HttpStatus.BAD_REQUEST),
    NOT_FOUND(603, HttpStatus.NOT_FOUND),

    INTERNAL_ERROR(600, HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_ACCESS_ERROR(605, HttpStatus.INTERNAL_SERVER_ERROR),

    UNAUTHORIZED(606, HttpStatus.UNAUTHORIZED),

    INVALID_EMAIL_AND_PASSWORD_REQUEST(400,HttpStatus.BAD_REQUEST),

    INVALID_REQUEST(400,HttpStatus.BAD_REQUEST),
    //refresh token 유효하지 않음
    NOTFOUND_REFRESHTOKEN(404,HttpStatus.NOT_FOUND),

    //refresh token 유효하지 않음
    INVALID_REFRESHTOKEN(410,HttpStatus.GONE),

    //현재 멤버와 불일치
    NOTCURRENT_MEMBER(400,HttpStatus.BAD_REQUEST),

    //이메일 인증 불일치
    EMAIL_CERTIFICATION_MISMATCH(400,HttpStatus.BAD_REQUEST),

    //잘못된 Login type
    NOTGENERAL_LOGIN(400,HttpStatus.BAD_REQUEST),

    //멤버 없음
    NOTFOUND_MEMBER(404,HttpStatus.NOT_FOUND),


    //메세지 없음
    NOTFOUND_MESSAGE(404,HttpStatus.NOT_FOUND),


    //게시글 없음
    NOTFOUND_BOARD(404,HttpStatus.BAD_REQUEST),

    //스크랩 없음
    NOTFOUND_SCRAPBOX(404,HttpStatus.NOT_FOUND),

    //중복 회원가입
    DUPLICATION_SIGNUP(400,HttpStatus.BAD_REQUEST),

    //이메일 중복
    DUPLICATION_EMAIL(409,HttpStatus.CONFLICT),

    //authority 불일치
    AUTHORITY_FORBIDDEN(403,HttpStatus.FORBIDDEN),

    //메세지 전송자와 요청자 불일치
    MISMATCH_SENDER(400,HttpStatus.BAD_REQUEST),

    //토큰 없음
    JWT_ACCESS_DENIED(401,HttpStatus.UNAUTHORIZED);


    private final Integer code;
    private final HttpStatus httpStatus;

    ErrorCode(Integer code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getMessage(Throwable e) {
        return e.getMessage();
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(message);
    }

    public static ErrorCode valueOf(HttpStatus httpStatus,Exception ex) {
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
                        System.out.println("ex = " + ex);
                        if(ex instanceof InternalAuthenticationServiceException || ex instanceof BadCredentialsException){

                            return ErrorCode.NOTFOUND_MEMBER;
                        }
                        else{
                            return ErrorCode.INTERNAL_ERROR;
                        }

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
