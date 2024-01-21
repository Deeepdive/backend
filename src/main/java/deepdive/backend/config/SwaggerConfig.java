package deepdive.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
    info = @Info(title = "Deep dive api 명세서",
        version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    // TODO : api 명세를 위한 토큰 발급 자동화
    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = HttpHeaders.AUTHORIZATION;
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
            .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                .name(jwtSchemeName)
                .type(Type.HTTP)
                .scheme(BEARER_TOKEN_PREFIX)
                .bearerFormat("SwaggerTokenTest")
            );

        return new OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
