package com.jybeomss1.wordbattle_backend.room.domain.dto;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import com.jybeomss1.wordbattle_backend.room.domain.RoomUser;
import java.util.Collections;
import java.util.UUID;

/**
 * 방 생성 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateRequest {
    private String roomName;
    private String password;
    private int quizCount;
    private UUID userId;

    public Room toEntity(RoomUser hostUser, String encodedPassword) {
        boolean hasPassword = password != null && !password.isEmpty();
        return Room.builder()
                .name(roomName)
                .password(encodedPassword)
                .gameCount(quizCount)
                .status(GameStatus.WAITING)
                .users(Collections.singletonList(hostUser))
                .hostUserId(hostUser.getId())
                .hasPassword(hasPassword)
                .build();
    }
} 