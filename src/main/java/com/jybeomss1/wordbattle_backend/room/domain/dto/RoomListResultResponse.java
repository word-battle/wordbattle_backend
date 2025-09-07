package com.jybeomss1.wordbattle_backend.room.domain.dto;

import com.jybeomss1.wordbattle_backend.room.domain.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomListResultResponse {
    private String roomId;
    private String roomName;
    private int currentUserCount;
    private boolean hasPassword;
    private String status;

    public static RoomListResultResponse fromRoom(Room room) {
        return RoomListResultResponse.builder()
                .roomId(room.getId().toString())
                .roomName(room.getName())
                .currentUserCount(room.getUsers().size())
                .hasPassword(room.isHasPassword())
                .status(room.getStatus().name())
                .build();
    }
}