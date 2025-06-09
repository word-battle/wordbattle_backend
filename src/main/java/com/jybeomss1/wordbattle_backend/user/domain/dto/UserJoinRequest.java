package com.jybeomss1.wordbattle_backend.user.domain.dto;

import lombok.Getter;

@Getter
public class UserJoinRequest {
    private String email;
    private String name;
    private String password;
}
