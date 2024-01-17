package deepdive.backend.jwt.domain;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@NoArgsConstructor
public class JsonWebToken {

    @Value("${}")

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    private String oauthId;
    private String refreshToken;
    private LocalDate expiredDate;

    public JsonWebToken(String oauthId, String refreshToken) {
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

    // TODO : 추후 update, validation 은 서비스단으로 분리
    public void validateRefreshToken(String refreshToken) {
        if (!refreshToken.equals(this.refreshToken)) {
            throw new JwtException("refreshToken이 일치하지 않습니다");
        }
    }
}
