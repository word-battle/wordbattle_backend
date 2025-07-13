package com.jybeomss1.wordbattle_backend.room.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomJoinResponse {
    private String roomId;
    private String roomName;
    private int currentUsers;
    private boolean hasPassword;
    private int roundCount;

    public static RoomJoinResponse from(com.jybeomss1.wordbattle_backend.room.domain.Room room) {
        return RoomJoinResponse.builder()
                .roomId(room.getId().toString())
                .roomName(room.getName())
                .currentUsers(room.getUsers() != null ? room.getUsers().size() : 0)
                .hasPassword(room.isHasPassword())
                .roundCount(room.getRoundCount())
                .build();
    }
} 