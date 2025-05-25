package Utils;

public class Response<T> {
    private final ResponseCode code;
    private final String message;
    private final T data;

    // Constructor para respuestas sin datos
    public Response(ResponseCode code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    // Constructor para respuestas con datos
    public Response(ResponseCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Getters
    public ResponseCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data != null ? CloneUtils.clone(data) : null;
    }
}