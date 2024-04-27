package prography.team5.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prography.team5.server.controller.dto.CommonApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleException(final Exception e) {
        log.error("알 수 없는 에러 발생", e);
        return ResponseEntity.internalServerError()
                .body(new CommonApiResponse<>(-1, "서버에서 장애가 발생했습니다."));
    }
}
