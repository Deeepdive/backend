package deepdive.backend.jwt.service;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.jwt.domain.JsonWebToken;
import deepdive.backend.jwt.domain.dto.ReIssueDto;
import deepdive.backend.jwt.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtRepository tokenRepository;

    @Value("${jwt.token.secret}")
    private String secret_code;
    @Value("${refreshtoken.expires}")
    private Long refreshTokenExpiration;
    @Value("${accesstoken.expires}")
    private Long accessTokenExpiration;
    @Value("${issuer}")
    private String issuer;

    @Transactional
    public void updateRefreshToken(String oauthId, String email) {
        JsonWebToken refreshToken = tokenRepository.findByOauthId(oauthId)
            .orElseThrow(ExceptionStatus.INVALID_ARGUMENT::asServiceException);

        log.info("member OauthID : {}", refreshToken.getOauthId());
        String createdRefreshToken = createRefreshToken(oauthId, email);

        refreshToken.updateRefreshToken(createdRefreshToken);
    }

    public String reissueAccessToken(ReIssueDto reIssueDto) {

        // TODO : dto에서 oauthID 받지 말고, ContextHolder 에서 찾기, email은.. 뭐로하지
        JsonWebToken memberToken = tokenRepository.findByOauthId(reIssueDto.getOauthId())
            .orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);

        // TODO : 추후 service 분리
        memberToken.validateRefreshToken(reIssueDto.getRefreshToken());

        return createAccessToken(reIssueDto.getOauthId(), "name");
    }

    public String createRefreshToken(String oauthId, String email) {
        return createToken(oauthId, email, refreshTokenExpiration);
    }

    public String createAccessToken(String oauthId, String email) {
        return createToken(oauthId, email, accessTokenExpiration);
    }

    private String createToken(String oauthId, String email, Long expireTime) {
        Claims claims = Jwts.claims().setSubject("UserToken");
        claims.put("oauthId", oauthId);
        claims.put("email", email);
        // TODO : role 분리 생각..
        claims.put("roles", "User");
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + 600 * expireTime))
            .signWith(generateSecretKey(secret_code), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key generateSecretKey(String secretCode) {
        String encodeSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodeSecretCode.getBytes());
    }
}
