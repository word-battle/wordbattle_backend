package com.jybeomss1.wordbattle_backend.game.application.port.out;

import com.jybeomss1.wordbattle_backend.game.domain.Game;
import java.util.Optional;
import java.util.UUID;

public interface GamePort {
    Game save(Game game);
    Optional<Game> findById(UUID gameId);
    Game findCurrentGameByRoomId(UUID roomId);
} 