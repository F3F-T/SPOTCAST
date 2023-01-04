package f3f.global.response;

import lombok.Getter;

@Getter
public class ResultDataResponseDTO<T> extends ResponseDTO  {

    private final T data;

    private ResultDataResponseDTO(T data) {
        super(true, ErrorCode.OK.getCode());
        this.data = data;
    }

    private ResultDataResponseDTO(T data, String message) {
        super(true, ErrorCode.OK.getCode(), message);
        this.data = data;
    }

    public static <T> ResultDataResponseDTO<T> of(T data) {
        return new ResultDataResponseDTO<>(data);
    }

    public static <T> ResultDataResponseDTO<T> of(T data, String message) {
        return new ResultDataResponseDTO<>(data, message);
    }

    public static <T> ResultDataResponseDTO<T> empty() {
        return new ResultDataResponseDTO<>(null);
    }
}


