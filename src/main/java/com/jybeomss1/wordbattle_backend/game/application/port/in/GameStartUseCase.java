package com.jybeomss1.wordbattle_backend.game.application.port.in;

import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;

import java.util.UUID;

/**
 * 게임 시작 유스케이스 인터페이스
 */
public interface GameStartUseCase {
    GameStatusResponse startGame(UUID roomId);
} 