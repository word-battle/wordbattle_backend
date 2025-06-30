package com.jybeomss1.wordbattle_backend.jwt;


import com.jybeomss1.wordbattle_backend.common.exceptions.InvalidatedTokenException;
import com.jybeomss1.wordbattle_backend.common.exceptions.RevokedTokenException;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (Objects.equals(uri, "/api/v1/user/login") || Objects.equals(uri, "/api/v1/user/join")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken  = jwtTokenProvider.resolveToken(request.getHeader("Authorization"));
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request.getHeader("X-Refresh-Token"));

        try {
            if (accessToken != null && jwtTokenProvider.isValidToken(accessToken)) {
                // 토큰이 블랙리스트에 올라가 있는지 먼저 확인
                String jti = jwtTokenProvider.getJti(accessToken);
                if (redisRefreshTokenRepository.getKey("blacklist:" + jti)) {
                    throw new RevokedTokenException();
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
                    throw new InvalidatedTokenException();
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
