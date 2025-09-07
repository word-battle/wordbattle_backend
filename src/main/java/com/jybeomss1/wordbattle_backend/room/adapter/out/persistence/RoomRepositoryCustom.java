package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {
    Page<RoomJpaEntity> findRoomsWithPaging(GameStatus gameStatus, Pageable pageable);
} 