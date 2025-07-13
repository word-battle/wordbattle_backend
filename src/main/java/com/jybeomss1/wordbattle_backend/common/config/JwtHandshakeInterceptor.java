package com.jybeomss1.wordbattle_backend.common.config;

import com.jybeomss1.wordbattle_backend.jwt.JwtTokenProvider;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    public JwtHandshakeInterceptor(JwtTokenProvider jwtTokenProvider, RedisRefreshTokenRepository redisRefreshTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String token = null;
        String refreshToken = null;
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            token = jwtTokenProvider.resolveToken(req.getHeader("Authorization"));
            refreshToken = jwtTokenProvider.resolveRefreshToken(req.getHeader("X-Refresh-Token"));
        }
        try {
            if (token != null && jwtTokenProvider.isValidToken(token)) {
                String jti = jwtTokenProvider.getJti(token);
                if (redisRefreshTokenRepository.getKey("blacklist:" + jti)) {
                    return false;
                }
                String userId = jwtTokenProvider.getUserId(token);
                attributes.put("userId", userId);
                return true;
            } else if (token != null && jwtTokenProvider.isExpired(token) && refreshToken != null) {
                String userId = jwtTokenProvider.getUserId(refreshToken);
                if (jwtTokenProvider.isValidRefreshToken(userId, refreshToken)) {
                    attributes.put("userId", userId);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
} 