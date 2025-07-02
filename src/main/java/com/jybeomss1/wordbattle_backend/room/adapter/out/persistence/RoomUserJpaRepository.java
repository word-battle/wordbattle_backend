package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomUserJpaRepository extends JpaRepository<RoomUserJpaEntity, UUID> {
} 