package prography.team5.server.exception;

import lombok.Getter;

@Getter
public class SachosaengException extends RuntimeException {

    private final ErrorType errorType;

    public SachosaengException(final ErrorType errorType) {
        super();
        this.errorType = errorType;
    }

    public int getCode() {
        return errorType.getCode();
    }
    @Override
    public String getMessage() {
        return errorType.getMessage();
    }
}
