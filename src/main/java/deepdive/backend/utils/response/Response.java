package deepdive.backend.utils.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response<T> {

    private final int statusCode;
    private final T data;

    public Response(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> Response<T> of(int statusCode, T data) {
        return new Response<>(statusCode, data);
    }

}
