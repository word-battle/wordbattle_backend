package com.jybeomss1.wordbattle_backend.game.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.game.application.port.out.GamePort;
import com.jybeomss1.wordbattle_backend.game.domain.Game;
import com.jybeomss1.wordbattle_backend.room.adapter.out.persistence.RoomJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameJpaAdapter implements GamePort {
    private final GameJpaRepository gameJpaRepository;

    @Override
    public Game save(Game game) {
        // Room 도메인 → RoomJpaEntity 변환 필요
        RoomJpaEntity roomEntity = RoomJpaEntity.fromDomain(game.getRoom());
        GameJpaEntity entity = GameJpaEntity.fromDomain(game, roomEntity);
        GameJpaEntity saved = gameJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Game> findById(java.util.UUID gameId) {
        return gameJpaRepository.findById(gameId)
                .map(GameJpaEntity::toDomain);
    }
} 