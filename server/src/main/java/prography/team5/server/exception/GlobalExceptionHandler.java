package prography.team5.server.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static prography.team5.server.exception.ErrorType.ACCESS_TOKEN_EXPIRATION;
import static prography.team5.server.exception.ErrorType.ACCESS_TOKEN_MALFORMED;
import static prography.team5.server.exception.ErrorType.ACCESS_TOKEN_SIGNATURE_FAIL;
import static prography.team5.server.exception.ErrorType.ACCESS_TOKEN_UNSUPPORTED;
import static prography.team5.server.exception.ErrorType.DUPLICATED_CATEGORY;
import static prography.team5.server.exception.ErrorType.DUPLICATED_EMAIL;
import static prography.team5.server.exception.ErrorType.INVALID_AUTHORIZATION_HEADER_FORM;
import static prography.team5.server.exception.ErrorType.INVALID_INFORMATION_CARD_ID;
import static prography.team5.server.exception.ErrorType.INVALID_CATEGORY;
import static prography.team5.server.exception.ErrorType.INVALID_EMAIL;
import static prography.team5.server.exception.ErrorType.INVALID_EMAIL_FORMAT;
import static prography.team5.server.exception.ErrorType.INVALID_REFRESH_TOKEN;
import static prography.team5.server.exception.ErrorType.INVALID_USER_ID;
import static prography.team5.server.exception.ErrorType.INVALID_USER_TYPE;
import static prography.team5.server.exception.ErrorType.NO_AUTHORIZATION_HEADER;
import static prography.team5.server.exception.ErrorType.NO_REFRESH_TOKEN;
import static prography.team5.server.exception.ErrorType.REFRESH_TOKEN_EXPIRATION;
import static prography.team5.server.exception.ErrorType.SERVER_ERROR;

import java.util.EnumMap;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prography.team5.server.controller.dto.CommonApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final EnumMap<ErrorType, HttpStatus> errorTypeToHttpStatus = new EnumMap<>(ErrorType.class);

    public GlobalExceptionHandler() {
        //500
        errorTypeToHttpStatus.put(SERVER_ERROR, INTERNAL_SERVER_ERROR);

        //401
        errorTypeToHttpStatus.put(NO_AUTHORIZATION_HEADER, UNAUTHORIZED);
        errorTypeToHttpStatus.put(INVALID_AUTHORIZATION_HEADER_FORM, UNAUTHORIZED);
        errorTypeToHttpStatus.put(ACCESS_TOKEN_EXPIRATION, UNAUTHORIZED);
        errorTypeToHttpStatus.put(ACCESS_TOKEN_MALFORMED, UNAUTHORIZED);
        errorTypeToHttpStatus.put(ACCESS_TOKEN_UNSUPPORTED, UNAUTHORIZED);
        errorTypeToHttpStatus.put(ACCESS_TOKEN_SIGNATURE_FAIL, UNAUTHORIZED);
        errorTypeToHttpStatus.put(NO_REFRESH_TOKEN, UNAUTHORIZED);
        errorTypeToHttpStatus.put(INVALID_REFRESH_TOKEN, UNAUTHORIZED);
        errorTypeToHttpStatus.put(REFRESH_TOKEN_EXPIRATION, UNAUTHORIZED);

        //400
        errorTypeToHttpStatus.put(DUPLICATED_EMAIL, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_EMAIL, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_EMAIL_FORMAT, BAD_REQUEST);
        errorTypeToHttpStatus.put(DUPLICATED_CATEGORY, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_CATEGORY, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_USER_ID, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_USER_TYPE, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_INFORMATION_CARD_ID, BAD_REQUEST);
    }

    @ExceptionHandler(SachosaengException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleSachosaengException(final SachosaengException e) {
        HttpStatus httpStatus = errorTypeToHttpStatus.getOrDefault(e.getErrorType(), null);
        if (Objects.isNull(httpStatus)) {
            log.warn("예외에 대한 상태코드 등록 필요: {}", e.getErrorType().toString());
            httpStatus = BAD_REQUEST; //임시로 400 반환
        }
        return ResponseEntity.status(httpStatus)
                .body(new CommonApiResponse<>(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleException(final Exception e) {
        final ErrorType errorType = SERVER_ERROR;
        log.error(errorType.getMessage(), e);
        return ResponseEntity.status(errorTypeToHttpStatus.get(errorType))
                .body(new CommonApiResponse<>(errorType.getCode(), errorType.getMessage()));
    }
}
