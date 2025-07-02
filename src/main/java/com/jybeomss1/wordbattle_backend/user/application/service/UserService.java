package com.jybeomss1.wordbattle_backend.user.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.jwt.JwtTokenProvider;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLogoutUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.out.UserPort;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserJoinRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserJoinUseCase, UserLoginUseCase, UserLogoutUseCase {
    private final UserPort userPort;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void join(UserJoinRequest request) {
        if (userPort.findByEmail(request.getEmail()).isPresent()) {
            log.warn("이미 존재하는 이메일로 회원가입 시도: {}", request.getEmail());
            throw new BaseException(ErrorCode.EXIST_USER);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userPort.save(request.getEmail(), request.getName(), encodedPassword);
        log.info("회원가입 성공: {}", request.getEmail());
    }

    @Override
    public TokenResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUserId().toString();

        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        jwtTokenProvider.saveRefreshToken(userId, refreshToken);
        log.info("로그인 성공: {}", email);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public void logout(String authorization) {
        String accessToken = jwtTokenProvider.resolveToken(authorization);
        String userId = jwtTokenProvider.getUserId(accessToken);
        String jti = jwtTokenProvider.getJti(authorization);
        Date exp = Jwts.parser()
                .setSigningKey(jwtTokenProvider.getKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        long ttl = exp.getTime() - System.currentTimeMillis();
        if (ttl > 0) {
            redisRefreshTokenRepository.save("blacklist:" + jti, "logout", ttl);
        }

        redisRefreshTokenRepository.delete(userId);
        log.info("로그아웃 성공: {}", userId);
    }
}
