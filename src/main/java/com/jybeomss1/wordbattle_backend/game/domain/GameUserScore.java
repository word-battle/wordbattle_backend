package com.jybeomss1.wordbattle_backend.game.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameUserScore {
    private UUID userId;
    private int score;
} 