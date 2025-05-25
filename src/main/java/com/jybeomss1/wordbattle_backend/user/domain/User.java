package com.jybeomss1.wordbattle_backend.user.domain;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String name;
    private String socialType;
    private String socialId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
}
