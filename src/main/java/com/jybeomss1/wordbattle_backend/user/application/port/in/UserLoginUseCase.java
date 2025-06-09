package com.jybeomss1.wordbattle_backend.user.application.port.in;

import com.jybeomss1.wordbattle_backend.user.domain.dto.response.TokenResponse;

public interface UserLoginUseCase {
    TokenResponse login(String email, String password);
}
