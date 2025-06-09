package com.jybeomss1.wordbattle_backend.jwt;

import com.jybeomss1.wordbattle_backend.user.application.port.out.UserPort;
import com.jybeomss1.wordbattle_backend.user.application.service.UserService;
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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserPort userPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = resolveToken(request);
        String refreshToken = resolveRefreshToken(request);

        try {
            if (accessToken != null && jwtTokenProvider.isValidToken(accessToken)) {
                String userId = jwtTokenProvider.getUserId(accessToken);
                authenticate(userId);
            } else if (accessToken != null && jwtTokenProvider.isExpired(accessToken) && refreshToken != null) {
                // accessToken 만료, refreshToken 유효한지 확인
                String userId = jwtTokenProvider.getUserId(refreshToken);

                if (userPort.isValidRefreshToken(userId, refreshToken)) {
                    // refresh 유효 → 새 access 발급
                    String newAccessToken = jwtTokenProvider.createAccessToken(userId);
                    response.setHeader("Authorization", "Bearer " + newAccessToken); // 프론트에서 이걸 다시 저장
                    authenticate(userId);
                } else {
                    throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
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

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

    private String resolveRefreshToken(HttpServletRequest request) {
        String token = request.getHeader("X-Refresh-Token");
        return (token != null && token.startsWith("Bearer ")) ? token.substring(7) : null;
    }
}
