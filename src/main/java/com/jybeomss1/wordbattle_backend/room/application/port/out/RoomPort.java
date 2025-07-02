package com.jybeomss1.wordbattle_backend.room.application.port.out;

import com.jybeomss1.wordbattle_backend.room.domain.Room;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 방 관련 DB 접근 추상화 포트
 */
public interface RoomPort {
    Room save(Room room);
    Optional<Room> findById(UUID roomId);
    List<Room> findAll();
} 