package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomListUseCase {
    Page<RoomListResultResponse> getRoomList(GameStatus gameStatus, Pageable pageable);
} 