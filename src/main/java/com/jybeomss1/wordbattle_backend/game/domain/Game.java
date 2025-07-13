package com.jybeomss1.wordbattle_backend.game.domain;

import lombok.*;
import java.util.List;
import java.util.UUID;
import com.jybeomss1.wordbattle_backend.room.domain.Room;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private UUID id;
    private UUID roomId;
    private int roundCount;
    private List<GameUserScore> userScores;

    public void addScoreToUser(UUID userId, int score) {
        for (GameUserScore gus : userScores) {
            if (gus.getUserId().equals(userId)) {
                gus.setScore(gus.getScore() + score);
                return;
            }
        }
        userScores.add(new GameUserScore(userId, score));
    }

    public static Game of(Room room) {
        List<GameUserScore> scores = room.getUsers().stream()
            .map(u -> new GameUserScore(u.getUserId(), 0))
            .toList();
        return Game.builder()
            .roomId(room.getId())
            .roundCount(1) // 첫 라운드로 시작, 필요시 파라미터로 조정
            .userScores(scores)
            .build();
    }
} 