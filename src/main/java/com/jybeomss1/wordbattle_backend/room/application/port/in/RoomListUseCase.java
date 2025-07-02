package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResponse;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import java.util.List;

/**
 * 방 리스트 조회 유스케이스 인터페이스
 */
public interface RoomListUseCase {
    List<RoomListResponse> getRoomList(GameStatus gameStatus);
} 