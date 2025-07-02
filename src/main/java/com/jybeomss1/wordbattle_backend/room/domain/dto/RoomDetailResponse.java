package com.jybeomss1.wordbattle_backend.room.domain.dto;

import com.jybeomss1.wordbattle_backend.room.domain.Room;
import com.jybeomss1.wordbattle_backend.room.domain.RoomUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDetailResponse {
    private String roomId;
    private String roomName;
    private int gameCount;
    private boolean hasPassword;
    private List<RoomUser> users;

    public static RoomDetailResponse from(Room room) {
        return RoomDetailResponse.builder()
                .roomId(room.getId().toString())
                .roomName(room.getName())
                .gameCount(room.getGameCount())
                .hasPassword(room.isHasPassword())
                .users(room.getUsers())
                .build();
    }

    public static RoomDetailResponse toStringId(RoomDetailResponse response) {
        return RoomDetailResponse.builder()
            .roomId(response.getRoomId())
            .roomName(response.getRoomName())
            .gameCount(response.getGameCount())
            .hasPassword(response.isHasPassword())
            .users(response.getUsers())
            .build();
    }
} 