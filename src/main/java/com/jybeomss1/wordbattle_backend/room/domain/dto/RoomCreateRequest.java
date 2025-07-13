package com.jybeomss1.wordbattle_backend.room.domain.dto;

import com.jybeomss1.wordbattle_backend.common.util.JoinCodeGenerator;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import com.jybeomss1.wordbattle_backend.room.domain.RoomUser;
import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateRequest {
    private String roomName;
    private String password;
    private int roundCount;

    public Room toEntity(RoomUser hostUser, String encodedPassword) {
        boolean hasPassword = password != null && !password.isEmpty();
        return Room.builder()
                .name(roomName)
                .password(encodedPassword)
                .roundCount(roundCount)
                .status(GameStatus.WAITING)
                .users(Collections.singletonList(hostUser))
                .hostUserId(hostUser.getUserId())
                .hasPassword(hasPassword)
                .joinCode(JoinCodeGenerator.generateJoinCode())
                .build();
    }
} 