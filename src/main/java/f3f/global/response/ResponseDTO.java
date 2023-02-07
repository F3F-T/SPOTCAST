package f3f.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ResponseDTO {

    private Boolean success;
    private Integer code;
    private String message;

    public ResponseDTO(Boolean success, Integer code) {
        this.success = success;
        this.code = code;
    }
    public static ResponseDTO of(Boolean success, ErrorCode code) {
        return new ResponseDTO(success, code.getCode());
    }

    public static ResponseDTO of(Boolean success, ErrorCode errorCode, Exception e) {
        return new ResponseDTO(success, errorCode.getCode(), errorCode.getMessage(e));
    }

    public static ResponseDTO of(Boolean success, ErrorCode errorCode, String message) {
        return new ResponseDTO(success, errorCode.getCode(), errorCode.getMessage(message));
    }
}

