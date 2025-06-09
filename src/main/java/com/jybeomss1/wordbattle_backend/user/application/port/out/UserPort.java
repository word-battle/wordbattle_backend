package com.jybeomss1.wordbattle_backend.user.application.port.out;

import com.jybeomss1.wordbattle_backend.user.domain.User;

import java.util.Optional;

public interface UserPort {
    Optional<User> findByEmail(String email);
    void save(String email, String name, String password);
    void saveRefreshToken(String userId, String refreshToken);
    boolean isValidRefreshToken(String userId, String refreshToken);
}
