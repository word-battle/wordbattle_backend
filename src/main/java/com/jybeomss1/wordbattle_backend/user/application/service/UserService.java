package com.jybeomss1.wordbattle_backend.user.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.ExistUserException;
import com.jybeomss1.wordbattle_backend.jwt.JwtTokenProvider;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.out.UserPort;
import com.jybeomss1.wordbattle_backend.user.domain.User;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserJoinRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserJoinUseCase, UserLoginUseCase {
    private final UserPort userPort;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(UserJoinRequest request) {
        Optional<User> existedUser = userPort.findByEmail(request.getEmail());
        if (existedUser.isPresent()) {
            throw new ExistUserException();
        }

        userPort.save(request.getEmail(), request.getName(), passwordEncoder.encode(request.getPassword()));
    }

    @Override
    public TokenResponse login(String email, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CustomUserDetails userDetail = (CustomUserDetails) authenticate.getPrincipal();
        String userId = userDetail.getUserId();

        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        userPort.saveRefreshToken(userId, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
