package com.jybeomss1.wordbattle_backend.room.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * 방에 속한 유저(RoomUser) 도메인 모델
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomUser {
    private UUID userId;
    private String name;
    private int score;

    public static RoomUser fromHostInfo(String userId, String name) {
        return RoomUser.builder()
                .userId(UUID.fromString(userId))
                .name(name)
                .score(0)
                .build();
    }

    public static RoomUser fromJoinInfo(String userId, String name) {
        return RoomUser.builder()
                .userId(UUID.fromString(userId))
                .name(name)
                .score(0)
                .build();
    }
} 