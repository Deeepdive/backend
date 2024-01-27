package deepdive.backend.config;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticateMatchers {

    public static final String[] swaggerArray = {
        "/api-docs",
        "/swagger-ui-custom.html",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        
    };
}
