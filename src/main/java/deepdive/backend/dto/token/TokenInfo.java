package deepdive.backend.dto.token;

public record TokenInfo(
    String accessToken,
    String refreshToken
) {

}
