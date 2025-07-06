package com.jybeomss1.wordbattle_backend.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("WordBattle API")
                .description("WordBattle 게임 API 문서")
                .version("1.0.0");

        // access 토큰 (Authorization 헤더)
        SecurityScheme accessScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // refresh 토큰 (X-Refresh-Token 헤더)
        SecurityScheme refreshScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-Refresh-Token");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth")
                .addList("refreshAuth");

        return new OpenAPI()
                .info(info)
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", accessScheme)
                        .addSecuritySchemes("refreshAuth", refreshScheme)
                )
                .addSecurityItem(securityRequirement);
    }
} 