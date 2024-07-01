package prography.team5.server.user.domain;

import static prography.team5.server.common.exception.ErrorType.INVALID_USER_TYPE;

import java.util.Arrays;
import prography.team5.server.common.exception.SachosaengException;

public enum UserType {

    STUDENT("학생"),
    JOB_SEEKER("취준생"),
    NEW_EMPLOYEE("입사 1~3년차 직장인"),
    OTHER("기타"),
    UNDEFINED("알수 없음");

    private String description;

    UserType(final String description) {
        this.description = description;
    }

    public static UserType convert(final String userType) {
        return Arrays.stream(values())
                .filter(each -> each.name().equalsIgnoreCase(userType))
                .findFirst()
                .orElseThrow(() -> new SachosaengException(INVALID_USER_TYPE));
    }

    public String getDescription() {
        return description;
    }
}
