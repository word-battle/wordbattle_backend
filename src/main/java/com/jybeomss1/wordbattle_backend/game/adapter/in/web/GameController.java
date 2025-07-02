package com.jybeomss1.wordbattle_backend.game.adapter.in.web;

import com.jybeomss1.wordbattle_backend.common.annotation.GameStartSwaggerDoc;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameStartUseCase;
import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 게임 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {
    private final GameStartUseCase gameStartUseCase;

    @GameStartSwaggerDoc
    @PostMapping("/start/{roomId}")
    public ResponseEntity<GameStatusResponse> startGame(@PathVariable String roomId) {
        return ResponseEntity.ok(GameStatusResponse.toStringId(gameStartUseCase.startGame(UUID.fromString(roomId))));
    }
} 