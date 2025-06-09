package com.jybeomss1.wordbattle_backend.user.domain;

import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.UserJpaEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final String socialType;
    private final String socialId;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final boolean deleted;

    public User(UserJpaEntity userJpaEntity) {
       this.id = userJpaEntity.getId().toString();
       this.name = userJpaEntity.getName();
       this.email = userJpaEntity.getEmail();
       this.password = userJpaEntity.getPassword();
       this.socialType = userJpaEntity.getSocialType();
       this.socialId = userJpaEntity.getSocialId();
       this.createdDate = userJpaEntity.getCreatedDate();
       this.modifiedDate = userJpaEntity.getModifiedDate();
       this.deleted = userJpaEntity.isDeleted();
    }
}
