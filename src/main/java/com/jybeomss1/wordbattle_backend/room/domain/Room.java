package com.jybeomss1.wordbattle_backend.room.domain;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 방(Room) 도메인 모델
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    private UUID id;
    private String name;
    private String password;
    private int gameCount;
    private GameStatus status;
    private List<RoomUser> users;
    private boolean hasPassword;
    private UUID hostUserId;

    public static Room fromJoin(Room room, RoomUser joinUser) {
        List<RoomUser> updatedUsers = new ArrayList<>(room.getUsers());
        updatedUsers.add(joinUser);
        return Room.builder()
                .id(room.getId())
                .name(room.getName())
                .password(room.getPassword())
                .gameCount(room.getGameCount())
                .status(room.getStatus())
                .users(updatedUsers)
                .hasPassword(room.isHasPassword())
                .build();
    }

    public static Room withStatus(Room room, GameStatus status) {
        return Room.builder()
                .id(room.getId())
                .name(room.getName())
                .password(room.getPassword())
                .gameCount(room.getGameCount())
                .status(status)
                .users(room.getUsers())
                .hasPassword(room.isHasPassword())
                .build();
    }

    public Room increaseGameCount() {
        return Room.builder()
                .id(this.id)
                .name(this.name)
                .password(this.password)
                .gameCount(this.gameCount + 1)
                .status(this.status)
                .users(this.users)
                .hasPassword(this.hasPassword)
                .build();
    }
} 