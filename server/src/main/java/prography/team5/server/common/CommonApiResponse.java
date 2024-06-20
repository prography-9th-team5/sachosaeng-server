package prography.team5.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
public class CommonApiResponse<T> {

    private int code;
    private String message;
    @JsonInclude(Include.NON_NULL)
    private T data;

    public CommonApiResponse(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public CommonApiResponse(final Integer code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
