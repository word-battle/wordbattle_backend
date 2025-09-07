package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.application.port.out.RoomPort;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoomJpaAdapter implements RoomPort {
    private final RoomJpaRepository roomJpaRepository;

    @Override
    public Room save(Room room) {
        RoomJpaEntity entity = RoomJpaEntity.fromDomain(room);
        RoomJpaEntity saved = roomJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Room> findById(UUID roomId) {
        return roomJpaRepository.findById(roomId)
                .map(RoomJpaEntity::toDomain);
    }

    @Override
    public Page<Room> findRooms(GameStatus gameStatus, Pageable pageable) {
        return roomJpaRepository.findRoomsWithPaging(gameStatus, pageable)
                .map(RoomJpaEntity::toDomain);
    }

    @Override
    public Optional<Room> findByJoinCode(String joinCode) {
        return roomJpaRepository.findByJoinCode(joinCode)
                .map(RoomJpaEntity::toDomain);
    }
} 