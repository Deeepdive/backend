package deepdive.backend.jwt.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.dto.token.TokenInfo;
import deepdive.backend.jwt.domain.dto.ReIssueDto;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberQueryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
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

    private final MemberQueryService memberQueryService;
    @Value("${jwt.token.secret}")
    private String secret_code;
    @Value("${refreshtoken.expires}")
    private Long refreshTokenExpiration;
    @Value("${accesstoken.expires}")
    private Long accessTokenExpiration;
    @Value("${issuer}")
    private String issuer;

    public TokenInfo generateToken(Long memberId, String oauthId) {
        return new TokenInfo(createAccessToken(memberId), createRefreshToken(oauthId));
    }

    public String createRefreshToken(String oauthId) {
        Claims claims = Jwts.claims().setSubject("UserToken");
        claims.put("roles", "User");

        claims.put("oauth", oauthId);
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(now.getTime() + 600 * refreshTokenExpiration))
            .signWith(generateSecretKey(secret_code), SignatureAlgorithm.HS256)
            .compact();
    }

    public String createAccessToken(Long memberId) {
        Claims claims = Jwts.claims().setSubject("UserToken");
        // TODO : role 분리 생각..
        claims.put("roles", "User");
        claims.put("memberId", memberId);
        Date now = new Date();

        return Jwts.builder()
            .setSubject("UserToken")
            .setClaims(claims)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + 600 * accessTokenExpiration))
            .signWith(generateSecretKey(secret_code), SignatureAlgorithm.HS256)
            .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseToken(accessToken);
        log.info("token info: {}", claims);

        if (claims.get("roles") == null) {
            throw new IllegalArgumentException("권한 정보가 존재하지 않습니다.");
        }

        List<SimpleGrantedAuthority> authorities = Stream.of(claims.get("roles").toString())
            .map(SimpleGrantedAuthority::new)
            .toList();

        Long memberId = claims.get("memberId", Long.class);

        AuthUserInfo authUserInfo = AuthUserInfo.builder()
            .memberId(memberId)
            .build();

        return new UsernamePasswordAuthenticationToken(authUserInfo, "", authorities);
    }

    public String reissueAccessToken(ReIssueDto reIssueDto) {

        // refresh encrypt 후에 괜찮은 애라면 재발급 ㄱㄱ
        Claims claims = parseToken(reIssueDto.getRefreshToken());
        String oauthId = claims.get("oauthId", String.class);
        Member member = memberQueryService.getMember();

        return createAccessToken(member.getId());
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(generateSecretKey(secret_code))
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (MalformedJwtException e) {
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
}
