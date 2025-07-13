package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomJoinRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomJoinResponse;

import java.util.UUID;

public interface RoomJoinUseCase {
    RoomJoinResponse joinRoom(RoomJoinRequest request, UUID userId, String name);

    RoomJoinResponse joinRoomByJoinCode(String joinCode, UUID userId, String name);
} 