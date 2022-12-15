package f3f.global.constants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConstants {
    public static final ResponseEntity<String> DUPLICATION_EMAIL = new ResponseEntity<>(
            "이메일 중복", HttpStatus.CONFLICT);
    public static final ResponseEntity<String> UnAuthorization = new ResponseEntity<>(
            "authority 불일치", HttpStatus.FORBIDDEN);
    public static final ResponseEntity<String> JwtAccessDenied = new ResponseEntity<>(
            "토큰 없음", HttpStatus.OK);

}
