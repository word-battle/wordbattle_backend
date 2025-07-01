package com.jybeomss1.wordbattle_backend.user.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;
    private String name;
    private String password;
}
