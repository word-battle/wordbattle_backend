package com.jybeomss1.wordbattle_backend.room.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 방 리스트 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomListResponse {
    private String roomId;
    private String roomName;
    private int currentUsers;
    private int quizCount;
    private boolean hasPassword;

    public static RoomListResponse fromRoom(com.jybeomss1.wordbattle_backend.room.domain.Room room) {
        return RoomListResponse.builder()
                .roomId(room.getId().toString())
                .roomName(room.getName())
                .currentUsers(room.getUsers() != null ? room.getUsers().size() : 0)
                .quizCount(room.getGameCount())
                .hasPassword(room.getPassword() != null && !room.getPassword().isEmpty())
                .build();
    }

    public static List<RoomListResponse> fromRooms(List<com.jybeomss1.wordbattle_backend.room.domain.Room> rooms) {
        if (rooms == null) return java.util.Collections.emptyList();
        return rooms.stream().map(RoomListResponse::fromRoom).toList();
    }

    public static RoomListResponse toStringId(RoomListResponse response) {
        return RoomListResponse.builder()
            .roomId(response.getRoomId())
            .roomName(response.getRoomName())
            .currentUsers(response.getCurrentUsers())
            .build();
    }
} 