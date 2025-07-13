package com.jybeomss1.wordbattle_backend.game.application.port.in;

import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;
import java.util.UUID;

public interface GameQueryUseCase {
    GameStatusResponse findCurrentGameByRoomId(UUID roomId);
} 