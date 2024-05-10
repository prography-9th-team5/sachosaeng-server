package prography.team5.server.exception;

public enum ErrorType {
    //예상치 못한 에러
    SERVER_ERROR(-1, "서버에서 장애가 발생했습니다."),

    //인증 관련 에러 - 엑세스 토큰
    NO_AUTHORIZATION_HEADER(-2, "Authorization 헤더가 비어있습니다."),
    INVALID_AUTHORIZATION_HEADER_FORM(-2, "Authorization 헤더가 올바른 형식이 아닙니다."),
    ACCESS_TOKEN_EXPIRATION(-2, "Access Token이 만료되었습니다. 재발급이 필요합니다."),
    ACCESS_TOKEN_MALFORMED(-2, "올바른 형식으로 구성된 Access Token이 아닙니다."),
    ACCESS_TOKEN_UNSUPPORTED(-2, "지원하는 형식의 Access Token이 아닙니다."),
    ACCESS_TOKEN_SIGNATURE_FAIL(-2, "Access Token의 signature 검증이 실패하였습니다."),

    //인증 관련 에러 - 리프레시 토큰
    NO_REFRESH_TOKEN(-3, "Refresh Token이 없습니다."),
    INVALID_REFRESH_TOKEN(-3, "유효하지 않은 Refresh Token입니다."),
    REFRESH_TOKEN_EXPIRATION(-3, "Refresh Token이 만료되었습니다. 다시 로그인이 필요합니다."),

    //인증 관련 에러 - 이메일
    DUPLICATED_EMAIL(-4, "이미 가입한 적이 있는 이메일입니다."),
    INVALID_EMAIL(-4, "존재하지 않는 이메일입니다.");

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
