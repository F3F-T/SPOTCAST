package f3f.global.exception;

import f3f.domain.user.exception.DuplicateEmailException;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MemberNotFoundException.class)
    protected final ResponseEntity<String> handleMemberNotFoundException(
            MemberNotFoundException ex, WebRequest request) {
        log.debug("member not found :: {}, detection time = {}",ex.getMessage());
        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
