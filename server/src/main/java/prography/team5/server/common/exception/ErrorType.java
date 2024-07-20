package prography.team5.server.common.exception;

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

    //이메일 에러
    DUPLICATED_EMAIL(-4, "이미 가입한 적이 있는 이메일입니다."),
    INVALID_EMAIL(-4, "존재하지 않는 이메일입니다."),
    INVALID_EMAIL_FORMAT(-4, "올바른 이메일 형식이 아닙니다."),

    //카테고리 에러
    DUPLICATED_CATEGORY(-5, "이미 존재하는 카테고리입니다."),
    INVALID_CATEGORY(-5, "존재하지 않는 카테고리입니다."),

    //유저 에러
    INVALID_USER_ID(-6, "존재하지 않는 유저입니다."),
    INVALID_USER_TYPE(-6, "존재하지 않는 유저 타입입니다."),

    //정보 카드 에러
    INVALID_INFORMATION_CARD_ID(-7, "존재하지 않는 정보id입니다."),

    //처리할 수 없는 url
    PAGE_NOT_FOUND(-8, "해당 url을 찾을 수 없습니다."),

    //투표 에러
    INVALID_VOTE_CARD_ID(-9, "존재하지 않는 투표id입니다."),
    INVALID_VOTE_OPTION_ID(-9, "해당 투표는 해당 투표 옵션을 가지고 있지 않습니다."),
    VOTE_OPTION_LIMIT(-9, "투표 옵션은 2개부터 4개까지만 가능합니다."),
    SAME_VOTE_OPTION(-9, "동일 옵션에서 동일 옵션으로 변경할 수 없습니다."),
    CATEGORY_NOT_INCLUDED_IN_VOTE(-9, "해당 투표는 해당 카테고리에 속하지 않습니다."),
    EMPTY_ADMIN_NAME(-9, "투표 생성 시 admin 이름이 필요합니다."),
    EMPTY_CATEGORY(-9, "투표 생성 시 카테고리가 필요합니다."),
    EMPTY_TITLE(-9, "투표 생성 시 투표 주제가 필요합니다."),
    MULTIPLE_CHOICE_NOT_ALLOWED(-9, "복수 선택이 허용되지 않은 투표입니다."),

    //정보 에러
    CATEGORY_NOT_INCLUDED_IN_INFORMATION(-10, "해당 콘텐츠는 해당 카테고리에 속하지 않습니다.");

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
