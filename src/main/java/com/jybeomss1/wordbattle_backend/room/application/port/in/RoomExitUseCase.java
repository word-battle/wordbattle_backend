package com.jybeomss1.wordbattle_backend.room.application.port.in;

import java.util.UUID;

public interface RoomExitUseCase {
    void exit(UUID roomId, UUID userId);
}
