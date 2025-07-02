package com.jybeomss1.wordbattle_backend.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import java.util.UUID;

/**
 * 게임(Game) 도메인 모델
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    private UUID id;
    private Room room;
    private int currentQuizIndex;

    public static Game of(Room room) {
        return Game.builder()
                .room(room)
                .currentQuizIndex(0)
                .build();
    }

    public Game increaseQuizIndex() {
        return Game.builder()
                .id(this.id)
                .room(this.room)
                .currentQuizIndex(this.currentQuizIndex + 1)
                .build();
    }
} 