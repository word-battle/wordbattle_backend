package com.jybeomss1.wordbattle_backend.jwt;

import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    public final String secretKey = Base64.getEncoder().encodeToString("qqwweerrttyyuuiiooppaassddffgghhjjkkllzzxxccvvbbnnmm".getBytes());
    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

    private static final long ACCESS_TOKEN_EXP = 1000L * 60 * 15;   // 15분
    public static final long REFRESH_TOKEN_EXP = 1000L * 60 * 60 * 24 * 7; // 7일

    public String createAccessToken(String userId) {
        return createToken(userId, ACCESS_TOKEN_EXP);
    }

    public String createRefreshToken(String userId) {
        return createToken(userId, REFRESH_TOKEN_EXP);
    }

    private String createToken(String userId, long validity) {
        Date now = new Date();
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .setId(jti)                  // JWT ID
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(key)
                .compact();
    }

    public String resolveToken(String authorization) {
        return (authorization != null && authorization.startsWith("Bearer ")) ? authorization.substring(7) : null;
    }

    public String resolveRefreshToken(String xRefreshToken) {
        return (xRefreshToken != null && xRefreshToken.startsWith("Bearer ")) ? xRefreshToken.substring(7) : null;
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException | JwtException e) {
            return false;
        }
    }

    public boolean isExpired(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public void saveRefreshToken(String userId, String refreshToken) {
        redisRefreshTokenRepository.save(userId, refreshToken, JwtTokenProvider.REFRESH_TOKEN_EXP);
    }

    public boolean isValidRefreshToken(String userId, String refreshToken) {
        return redisRefreshTokenRepository.isValid(userId, refreshToken);
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String getJti(String token) {
        return Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getId();
    }
}
