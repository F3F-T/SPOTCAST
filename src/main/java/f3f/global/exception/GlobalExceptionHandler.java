package f3f.global.exception;

import f3f.domain.user.exception.DuplicateEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static f3f.global.constants.ResponseConstants.DUPLICATION_EMAIL;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(InvalidJwtTokenException.class)
//    protected final ResponseEntity<String> handleJwtTokenException(
//            InvalidJwtTokenException  ex) {
//        log.debug("Wrong jwt :: {}, detection time = {}");
//        return JwtAccessDenied;
//    }
    @ExceptionHandler(DuplicateEmailException.class)
    protected final ResponseEntity<String> handleDuplicateEmailException(
            DuplicateEmailException ex, WebRequest request) {
        log.debug("Duplicate email :: {}, detection time = {}",request.getDescription(false));
        return DUPLICATION_EMAIL;
    }




}
