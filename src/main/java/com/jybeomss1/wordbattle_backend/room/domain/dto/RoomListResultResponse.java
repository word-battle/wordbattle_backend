package com.jybeomss1.wordbattle_backend.room.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomListResultResponse {
    private List<RoomListItem> rooms;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RoomListItem {
        private String roomId;
        private String roomName;
        private int currentUsers;
        private boolean hasPassword;
        private int roundCount;
    }

    public static RoomListResultResponse fromRooms(List<com.jybeomss1.wordbattle_backend.room.domain.Room> rooms) {
        return RoomListResultResponse.builder()
                .rooms(rooms.stream().map(room -> RoomListItem.builder()
                        .roomId(room.getId().toString())
                        .roomName(room.getName())
                        .currentUsers(room.getUsers() != null ? room.getUsers().size() : 0)
                        .hasPassword(room.isHasPassword())
                        .roundCount(room.getRoundCount())
                        .build()).toList())
                .build();
    }
} 