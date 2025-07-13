package com.jybeomss1.wordbattle_backend.game.application.port.in;

import java.util.UUID;

public interface GameScoreUseCase {
    void addScore(UUID gameId, String userId, int score);
} 