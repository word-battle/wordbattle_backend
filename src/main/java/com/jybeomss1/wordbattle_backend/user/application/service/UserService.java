package com.jybeomss1.wordbattle_backend.user.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.ExistUserException;
import com.jybeomss1.wordbattle_backend.common.exceptions.NotFoundUserException;
import com.jybeomss1.wordbattle_backend.jwt.JwtTokenProvider;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.RedisRefreshTokenRepository;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLogoutUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.out.UserPort;
import com.jybeomss1.wordbattle_backend.user.domain.User;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserJoinRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserJoinUseCase, UserLoginUseCase, UserLogoutUseCase, UserDetailsService {
    private final UserPort userPort;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Override
    public void join(UserJoinRequest request) {
        Optional<User> existedUser = userPort.findByEmail(request.getEmail());
        if (existedUser.isPresent()) {
            throw new ExistUserException();
        }

        userPort.save(request.getEmail(), request.getName(), request.getPassword());
    }

    @Override
    public TokenResponse login(String email, String password) {
        AuthenticationManager authenticationManager;
        try {
            authenticationManager = authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException("인증 매니저 가져오기 실패", e);
        }

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CustomUserDetails userDetail = (CustomUserDetails) authenticate.getPrincipal();
        String userId = userDetail.getUserId();

        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        jwtTokenProvider.saveRefreshToken(userId, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public void logout(String authorization) {
        String accessToken = jwtTokenProvider.resolveToken(authorization);
        String userId = jwtTokenProvider.getUserId(accessToken);
        String jti = jwtTokenProvider.getJti(authorization);
        Date exp = Jwts.parser()
                .setSigningKey(Decoders.BASE64.decode(jwtTokenProvider.secretKey))
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        long ttl = exp.getTime() - System.currentTimeMillis();
        if (ttl > 0) {
            redisRefreshTokenRepository.save("blacklist:" + jti, "logout", ttl);
        }

        redisRefreshTokenRepository.delete(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userPort.findByEmail(email).orElseThrow(NotFoundUserException::new);
        return new CustomUserDetails(user);
    }
}
