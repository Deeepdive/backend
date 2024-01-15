package deepdive.backend.auth.domain;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    private Long memberId;
    private String refreshToken;

    public JwtToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

    public void validateRefreshToken(String refreshToken) {
        if (!refreshToken.equals(this.refreshToken)) {
            throw new JwtException("refreshToken이 일치하지 않습니다");
        }
    }
}
