package Backend_TruckSnack.TruckSnack.util;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private HttpStatus status;
    private String message;

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;

    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static <T> ApiResponse<T> success(HttpStatus status, String message) {
        return new ApiResponse<>(status, message);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status, message);
    }
}
