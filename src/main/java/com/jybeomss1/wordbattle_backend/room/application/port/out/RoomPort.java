package com.jybeomss1.wordbattle_backend.room.application.port.out;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RoomPort {
    Room save(Room room);
    Optional<Room> findById(UUID roomId);
    Page<Room> findRooms(GameStatus gameStatus, Pageable pageable);
    Optional<Room> findByJoinCode(String joinCode);
} 