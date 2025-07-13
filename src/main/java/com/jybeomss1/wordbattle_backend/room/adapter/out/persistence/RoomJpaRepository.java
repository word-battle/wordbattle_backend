package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomJpaEntity, UUID> {
    // 추가 쿼리 메서드 정의 가능
    Optional<RoomJpaEntity> findByJoinCode(String joinCode);
} 