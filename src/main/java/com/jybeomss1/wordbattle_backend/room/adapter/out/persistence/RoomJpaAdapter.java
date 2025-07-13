package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.room.application.port.out.RoomPort;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
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
        // RoomJpaEntity → Room 매핑 (toDomain 메서드 사용)
        return saved.toDomain();
    }

    @Override
    public Optional<Room> findById(UUID roomId) {
        return roomJpaRepository.findById(roomId)
                .map(RoomJpaEntity::toDomain);
    }

    @Override
    public List<Room> findAll() {
        return roomJpaRepository.findAll().stream()
                .map(RoomJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Room> findByJoinCode(String joinCode) {
        return roomJpaRepository.findByJoinCode(joinCode)
                .map(RoomJpaEntity::toDomain);
    }
} 