package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomJoinRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResponse;
import java.util.UUID;

/**
 * 방 참가 유스케이스 인터페이스
 */
public interface RoomJoinUseCase {
    RoomListResponse joinRoom(RoomJoinRequest request, UUID userId, String name);
} 