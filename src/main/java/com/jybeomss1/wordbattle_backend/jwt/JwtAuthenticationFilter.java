package com.jybeomss1.wordbattle_backend.jwt;


import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import com.jybeomss1.wordbattle_backend.user.application.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    private static final Set<String> PERMIT_ALL_URIS = Set.of(
            "/api/v1/user/login",
            "/api/v1/user/join",
            "/swagger-ui.html",
            "/api-docs",
            "/.well-known/appspecific/com.chrome.devtools.json"
    );

    private static final List<String> PERMIT_ALL_PATTERNS = List.of(
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/v1/oauth2/authorize/**",
            "/api/v1/oauth2/callback/**"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (isPermitAllUri(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtTokenProvider.resolveToken(request.getHeader("Authorization"));
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request.getHeader("X-Refresh-Token"));

        try {
            if (accessToken != null && jwtTokenProvider.isValidToken(accessToken)) {
                // 토큰이 블랙리스트에 올라가 있는지 먼저 확인
                String jti = jwtTokenProvider.getJti(accessToken);
                if (redisRefreshTokenRepository.getKey("blacklist:" + jti)) {
                    throw new BaseException(ErrorCode.REVOKED_TOKEN);
                }

                // 블랙리스트가 아니면 정상 인증
                String userId = jwtTokenProvider.getUserId(accessToken);
                authenticate(userId);

            } else if (accessToken != null && jwtTokenProvider.isExpired(accessToken)
                    && refreshToken != null) {
                // accessToken 만료된 경우, refreshToken 으로 교체 발급 로직
                String userId = jwtTokenProvider.getUserId(refreshToken);
                if (jwtTokenProvider.isValidRefreshToken(userId, refreshToken)) {
                    String newAccessToken = jwtTokenProvider.createAccessToken(userId);
                    response.setHeader("Authorization", "Bearer " + newAccessToken);
                    authenticate(userId);
                } else {
                    throw new BaseException(ErrorCode.INVALID_TOKEN);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }


    private void authenticate(String userId) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean isPermitAllUri(String uri) {
        if (PERMIT_ALL_URIS.contains(uri)) return true;
        return PERMIT_ALL_PATTERNS.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }
}
