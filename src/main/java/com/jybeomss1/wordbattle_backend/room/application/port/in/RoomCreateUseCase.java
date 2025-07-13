package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateResponse;
import java.util.UUID;

public interface RoomCreateUseCase {
    RoomCreateResponse createRoom(RoomCreateRequest request, UUID userId, String name);
} 