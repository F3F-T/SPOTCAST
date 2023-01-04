package f3f.global.response;

public class ExceptionResponseDTO extends ResponseDTO {
    private ExceptionResponseDTO(ErrorCode errorCode) {
        super(false, errorCode.getCode());
    }

    private ExceptionResponseDTO(ErrorCode errorCode, Exception e) {
        super(false, errorCode.getCode(), errorCode.getMessage(e));
    }

    private ExceptionResponseDTO(ErrorCode errorCode, String message) {
        super(false, errorCode.getCode(), errorCode.getMessage(message));
    }


    public static ExceptionResponseDTO of(ErrorCode errorCode) {
        return new ExceptionResponseDTO(errorCode);
    }

    public static ExceptionResponseDTO of(ErrorCode errorCode, Exception e) {
        return new ExceptionResponseDTO(errorCode, e);
    }

    public static ExceptionResponseDTO of(ErrorCode errorCode, String message) {
        return new ExceptionResponseDTO(errorCode, message);
    }
}
