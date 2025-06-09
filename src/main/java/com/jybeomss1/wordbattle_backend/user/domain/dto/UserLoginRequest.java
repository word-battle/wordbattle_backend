package com.jybeomss1.wordbattle_backend.user.domain.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
