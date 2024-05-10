package prography.team5.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.exception.ErrorType;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //todo: 예외처리

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleException(final Exception e) {
        final ErrorType errorType = ErrorType.SERVER_ERROR;
        log.error(errorType.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(new CommonApiResponse<>(errorType.getCode(), errorType.getMessage()));
    }
}
