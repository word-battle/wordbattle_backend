package com.jybeomss1.wordbattle_backend.room.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomJoinRequest {
    private UUID roomId;
    private String password;
} 