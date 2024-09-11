package prography.team5.server.common;

import java.util.Collections;
import lombok.Getter;

@Getter
public class CommonApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public CommonApiResponse(final Integer code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
