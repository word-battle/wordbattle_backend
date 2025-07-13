package com.jybeomss1.wordbattle_backend.room.application.port.out;

import com.jybeomss1.wordbattle_backend.room.domain.Room;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomPort {
    Room save(Room room);
    Optional<Room> findById(UUID roomId);
    List<Room> findAll();
    Optional<Room> findByJoinCode(String joinCode);
} 