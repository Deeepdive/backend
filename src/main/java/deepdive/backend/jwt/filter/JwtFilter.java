package deepdive.backend.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import deepdive.backend.jwt.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request).orElse(null);

        if (token != null) {
            try {
                Authentication auth = jwtService.getAuthentication(token);
                log.info("accessToken 존재합니다, auth Info = {}", auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                sendRequest(response, HttpServletResponse.SC_UNAUTHORIZED, e.toString());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isEmpty(authorization)) {
            return Optional.empty();
        }
        return getToken(List.of(authorization.split(" ")));
    }

    private Optional<String> getToken(List<String> token) {
        if (token.size() != 2 || !token.get(0).equals("Bearer")) {
            return Optional.empty();
        }
        return Optional.ofNullable(token.get(1));
    }

    private void sendRequest(HttpServletResponse response, int errorCode, String errorMessage)
        throws IOException {

        response.setStatus(errorCode);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        String errResult = objectMapper.writeValueAsString(errorMessage);

        response.getWriter().write(errResult);
    }
}
