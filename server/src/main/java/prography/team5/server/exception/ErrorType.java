package prography.team5.server.exception;

public enum ErrorType {
    SERVER_ERROR(-1, "서버에서 장애가 발생했습니다.");

    private final int code;
    private final String message;

    ErrorType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
