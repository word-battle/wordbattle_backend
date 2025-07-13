package com.jybeomss1.wordbattle_backend.game.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, java.util.UUID> {
    Optional<GameJpaEntity> findTopByRoomIdOrderByRoundCountDesc(UUID roomId);
}