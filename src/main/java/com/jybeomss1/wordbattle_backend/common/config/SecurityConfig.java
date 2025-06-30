package com.jybeomss1.wordbattle_backend.common.config;

import com.jybeomss1.wordbattle_backend.jwt.JwtAuthenticationFilter;
import com.jybeomss1.wordbattle_backend.jwt.JwtTokenProvider;
import com.jybeomss1.wordbattle_backend.oauth.application.service.CustomOAuth2UserService;
import com.jybeomss1.wordbattle_backend.oauth.application.service.CustomOidcUserService;
import com.jybeomss1.wordbattle_backend.oauth.application.service.OAuth2AuthenticationSuccessHandler;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final CustomOidcUserService customOidcUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login", "/join",
                                "/swagger-ui.html",           // 리디렉트용
                                "/swagger-ui/**",             // Swagger UI 리소스
                                "/api-docs",               // 문서 루트
                                "/api-docs/**",            // 문서 상세
                                "/swagger-resources/**",      // 부가 리소스
                                "/webjars/**"         ,
                                "/.well-known/appspecific/com.chrome.devtools.json", // JS/CSS 등 웹 리소스,
                                "/api/v1/oauth2/authorize/**",   // ← 여기에 추가
                                "/api/v1/oauth2/callback/**"     // ← 여기에 추가
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri("/api/v1/oauth2/authorize")
                        )
                        .redirectionEndpoint(redir -> redir
                                .baseUri("/api/v1/oauth2/callback/*")
                        )
                        .userInfoEndpoint(userInfo ->
                                userInfo.oidcUserService(customOidcUserService)
                        )
                        .successHandler(successHandler)
                        .failureHandler((req, res, ex) ->
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService, redisRefreshTokenRepository),
                        UsernamePasswordAuthenticationFilter.class
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
