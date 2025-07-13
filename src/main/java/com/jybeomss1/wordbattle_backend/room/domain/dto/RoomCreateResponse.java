package com.jybeomss1.wordbattle_backend.room.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateResponse {
    private String roomId;
    private String roomName;
    private String joinCode;
    private boolean hasPassword;
    private int roundCount;

    public static RoomCreateResponse from(com.jybeomss1.wordbattle_backend.room.domain.Room room) {
        return RoomCreateResponse.builder()
                .roomId(room.getId().toString())
                .roomName(room.getName())
                .joinCode(room.getJoinCode())
                .hasPassword(room.isHasPassword())
                .roundCount(room.getRoundCount())
                .build();
    }
} 