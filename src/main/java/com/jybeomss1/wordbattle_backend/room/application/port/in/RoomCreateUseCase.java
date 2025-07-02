package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResponse;
import java.util.UUID;

/**
 * 방 생성 유스케이스 인터페이스
 */
public interface RoomCreateUseCase {
    RoomListResponse createRoom(RoomCreateRequest request, UUID userId, String name);
} 