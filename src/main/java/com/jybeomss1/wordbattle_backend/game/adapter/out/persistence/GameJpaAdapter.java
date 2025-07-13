package com.jybeomss1.wordbattle_backend.game.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.game.application.port.out.GamePort;
import com.jybeomss1.wordbattle_backend.game.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameJpaAdapter implements GamePort {
    private final GameJpaRepository gameJpaRepository;

    @Override
    public Game save(Game game) {
        GameJpaEntity entity = GameJpaEntity.fromDomain(game);
        GameJpaEntity saved = gameJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Game> findById(java.util.UUID gameId) {
        return gameJpaRepository.findById(gameId).map(GameJpaEntity::toDomain);
    }

    @Override
    public Game findCurrentGameByRoomId(UUID roomId) {
        return gameJpaRepository.findTopByRoomIdOrderByRoundCountDesc(roomId)
                .map(GameJpaEntity::toDomain)
                .orElse(null);
    }
}