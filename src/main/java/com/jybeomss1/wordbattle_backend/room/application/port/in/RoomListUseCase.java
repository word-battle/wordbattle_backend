package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResultResponse;

public interface RoomListUseCase {
    RoomListResultResponse getRoomList(GameStatus gameStatus);
} 