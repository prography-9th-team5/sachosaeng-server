package prography.team5.server.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static prography.team5.server.common.exception.ErrorType.ACCESS_TOKEN_EXPIRATION;
import static prography.team5.server.common.exception.ErrorType.ACCESS_TOKEN_MALFORMED;
import static prography.team5.server.common.exception.ErrorType.ACCESS_TOKEN_SIGNATURE_FAIL;
import static prography.team5.server.common.exception.ErrorType.ACCESS_TOKEN_UNSUPPORTED;
import static prography.team5.server.common.exception.ErrorType.CATEGORY_NOT_INCLUDED_IN_INFORMATION;
import static prography.team5.server.common.exception.ErrorType.DUPLICATED_CATEGORY;
import static prography.team5.server.common.exception.ErrorType.DUPLICATED_EMAIL;
import static prography.team5.server.common.exception.ErrorType.EMPTY_ADMIN_NAME;
import static prography.team5.server.common.exception.ErrorType.EMPTY_CATEGORY;
import static prography.team5.server.common.exception.ErrorType.EMPTY_TITLE;
import static prography.team5.server.common.exception.ErrorType.INVALID_AUTHORIZATION_HEADER_FORM;
import static prography.team5.server.common.exception.ErrorType.INVALID_CATEGORY;
import static prography.team5.server.common.exception.ErrorType.INVALID_EMAIL;
import static prography.team5.server.common.exception.ErrorType.INVALID_EMAIL_FORMAT;
import static prography.team5.server.common.exception.ErrorType.INVALID_INFORMATION_CARD_ID;
import static prography.team5.server.common.exception.ErrorType.INVALID_REFRESH_TOKEN;
import static prography.team5.server.common.exception.ErrorType.INVALID_USER_ID;
import static prography.team5.server.common.exception.ErrorType.INVALID_USER_TYPE;
import static prography.team5.server.common.exception.ErrorType.INVALID_VOTE_CARD_ID;
import static prography.team5.server.common.exception.ErrorType.INVALID_VOTE_OPTION_ID;
import static prography.team5.server.common.exception.ErrorType.MULTIPLE_CHOICE_NOT_ALLOWED;
import static prography.team5.server.common.exception.ErrorType.NO_AUTHORIZATION_HEADER;
import static prography.team5.server.common.exception.ErrorType.NO_REFRESH_TOKEN;
import static prography.team5.server.common.exception.ErrorType.PAGE_NOT_FOUND;
import static prography.team5.server.common.exception.ErrorType.REFRESH_TOKEN_EXPIRATION;
import static prography.team5.server.common.exception.ErrorType.SAME_VOTE_OPTION;
import static prography.team5.server.common.exception.ErrorType.SERVER_ERROR;
import static prography.team5.server.common.exception.ErrorType.VOTE_OPTION_LIMIT;

import java.util.EnumMap;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prography.team5.server.common.CommonApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final EnumMap<ErrorType, HttpStatus> errorTypeToHttpStatus = new EnumMap<>(ErrorType.class);

    public GlobalExceptionHandler() {
        //500
        errorTypeToHttpStatus.put(SERVER_ERROR, INTERNAL_SERVER_ERROR);

        //404
        errorTypeToHttpStatus.put(PAGE_NOT_FOUND, NOT_FOUND);

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
        errorTypeToHttpStatus.put(INVALID_VOTE_CARD_ID, BAD_REQUEST);
        errorTypeToHttpStatus.put(INVALID_VOTE_OPTION_ID, BAD_REQUEST);
        errorTypeToHttpStatus.put(VOTE_OPTION_LIMIT, BAD_REQUEST);
        errorTypeToHttpStatus.put(SAME_VOTE_OPTION, BAD_REQUEST);
        errorTypeToHttpStatus.put(EMPTY_ADMIN_NAME, BAD_REQUEST);
        errorTypeToHttpStatus.put(EMPTY_CATEGORY, BAD_REQUEST);
        errorTypeToHttpStatus.put(EMPTY_TITLE, BAD_REQUEST);
        errorTypeToHttpStatus.put(MULTIPLE_CHOICE_NOT_ALLOWED, BAD_REQUEST);
        errorTypeToHttpStatus.put(CATEGORY_NOT_INCLUDED_IN_INFORMATION, BAD_REQUEST);
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

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatusCode status,
                                                                   final WebRequest request) {
        final ErrorType errorType = PAGE_NOT_FOUND;
        log.warn(errorType.getMessage(), ex);
        return ResponseEntity.status(errorTypeToHttpStatus.get(errorType))
                .body(new CommonApiResponse<>(errorType.getCode(), errorType.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleException(final Exception e) {
        final ErrorType errorType = SERVER_ERROR;
        log.error(errorType.getMessage(), e);
        return ResponseEntity.status(errorTypeToHttpStatus.get(errorType))
                .body(new CommonApiResponse<>(errorType.getCode(), errorType.getMessage()));
    }
}
