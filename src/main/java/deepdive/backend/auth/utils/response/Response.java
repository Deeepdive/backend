package deepdive.backend.auth.utils.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {

    private int statusCode;
    private String responseMessage;

    public static Response of(int statusCode, String responseMessage) {
        return Response.builder()
            .statusCode(statusCode)
            .responseMessage(responseMessage)
            .build();
    }
}
