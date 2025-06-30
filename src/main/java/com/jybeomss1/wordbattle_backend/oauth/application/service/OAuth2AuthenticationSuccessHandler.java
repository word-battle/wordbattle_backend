package com.jybeomss1.wordbattle_backend.oauth.application.service;


import com.jybeomss1.wordbattle_backend.jwt.JwtTokenProvider;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.UserJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        DefaultOAuth2User oauthUser =
                (DefaultOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");

        // DB에서 userId 조회
        String userId = userJpaRepository.findByEmail(email)
                .orElseThrow()
                .getId().toString();

        // JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);
        jwtTokenProvider.saveRefreshToken(userId, refreshToken);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.setHeader("X-Refresh-Token", refreshToken);

        response.setHeader("Access-Control-Expose-Headers",
                HttpHeaders.AUTHORIZATION + ", X-Refresh-Token");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{}");
    }
}
