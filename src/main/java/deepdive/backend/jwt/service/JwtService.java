package deepdive.backend.jwt.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.jwt.domain.JsonWebToken;
import deepdive.backend.jwt.repository.JwtRepository;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;
    @Value("${jwt.token.secret}")
    private String secret_code;

    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        log.info("token info: {}", claims);

        if (claims.get("roles") == null) {
            throw new IllegalArgumentException("권한 정보가 존재하지 않습니다.");
        }

        List<SimpleGrantedAuthority> authorities = Stream.of(claims.get("roles").toString())
            .map(SimpleGrantedAuthority::new)
            .toList();

        String oauthId = claims.get("oauthId", String.class);
        String email = claims.get("email", String.class);

        Long memberId = memberRepository.findByEmail(email)
            .map(Member::getId)
            .orElse(-1L);
        // principal 구축
        AuthUserInfo authUserInfo = AuthUserInfo.builder()
            .oauthId(oauthId)
            .memberId(memberId)
            .email(email)
            .build();

        return new UsernamePasswordAuthenticationToken(authUserInfo, "", authorities);
    }

    private Claims parseToken(String accessToken) {
        log.info("토큰이 존재하나여.. : {}", accessToken);

        try {
            return Jwts.parserBuilder()
                .setSigningKey(generateSecretKey(secret_code))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (MalformedJwtException e) {
            // TODO : 추후에 CustomException 구축
            throw new JwtException("잘못된 형식의 토큰입니다");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원하지 않는 형식의 토큰입니다");
        } catch (IllegalArgumentException e) {
            throw new JwtException("잘못된 정보입니다.");
        } catch (SignatureException e) {
            throw new JwtException("시그니쳐가 이상한데요");
        }
    }

    private Key generateSecretKey(String secretCode) {
        String encodeSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodeSecretCode.getBytes());
    }

    @Transactional
    public void saveRefreshToken(String oauthId, String refreshToken) {
        JsonWebToken jsonWebToken = new JsonWebToken(oauthId, refreshToken);

        jwtRepository.save(jsonWebToken);
    }
}
