package com.jybeomss1.wordbattle_backend.user.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String name;

    @Column(name = "social_type")
    private String socialType;

    @Column(name = "social_id")
    private String socialId;

    @Column(nullable = false)
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }
}
