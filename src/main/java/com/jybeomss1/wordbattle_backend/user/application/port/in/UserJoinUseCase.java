package com.jybeomss1.wordbattle_backend.user.application.port.in;


import com.jybeomss1.wordbattle_backend.user.domain.dto.UserJoinRequest;

public interface UserJoinUseCase {
    void join(UserJoinRequest request);
}
