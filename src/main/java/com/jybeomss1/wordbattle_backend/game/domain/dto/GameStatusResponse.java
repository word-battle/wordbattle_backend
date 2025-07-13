package com.jybeomss1.wordbattle_backend.game.domain.dto;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.domain.RoomUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

/**
 * 게임 상태 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStatusResponse {
    private String roomId;
    private List<RoomUser> users;
    private String gameId;
    private int currentRoundCount;
    private GameStatus status;

    public static GameStatusResponse of(UUID gameId, int currentRoundCount) {
        return GameStatusResponse.builder()
                .gameId(gameId.toString())
                .currentRoundCount(currentRoundCount)
                .build();
    }

    public static GameStatusResponse toStringId(GameStatusResponse response) {
        return GameStatusResponse.builder()
            .gameId(response.getGameId())
            .currentRoundCount(response.getCurrentRoundCount())
            .build();
    }
} 