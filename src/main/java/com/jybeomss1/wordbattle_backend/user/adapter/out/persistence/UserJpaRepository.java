package com.jybeomss1.wordbattle_backend.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity,String> {
    Optional<UserJpaEntity> findByEmail(String email);
}
