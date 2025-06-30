package com.jybeomss1.wordbattle_backend.user.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.user.application.port.out.UserPort;
import com.jybeomss1.wordbattle_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findByEmail(email);
        return userJpaEntity.map(UserJpaEntity::toDto);
    }

    @Override
    public void save(String email, String name, String password) {
        userJpaRepository.save(new UserJpaEntity(email, name, password));
    }

}
