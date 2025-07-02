package com.jybeomss1.wordbattle_backend.game.application.port.out;

import com.jybeomss1.wordbattle_backend.game.domain.Game;
import java.util.Optional;

/**
 * 게임 관련 DB 접근 추상화 포트
 */
public interface GamePort {
    Game save(Game game);
    Optional<Game> findById(java.util.UUID gameId);
} 