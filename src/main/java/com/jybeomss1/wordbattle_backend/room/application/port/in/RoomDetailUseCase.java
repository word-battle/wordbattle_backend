package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomDetailResponse;
import java.util.UUID;

public interface RoomDetailUseCase {
    RoomDetailResponse getRoomDetail(UUID roomId);
} 