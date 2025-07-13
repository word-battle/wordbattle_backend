package com.jybeomss1.wordbattle_backend.game.adapter.in.web;

import com.jybeomss1.wordbattle_backend.common.annotation.GameCurrentSwaggerDoc;
import com.jybeomss1.wordbattle_backend.common.annotation.GameStartSwaggerDoc;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameQueryUseCase;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameStartUseCase;
import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {
    private final GameStartUseCase gameStartUseCase;
    private final GameQueryUseCase gameQueryUseCase;

    @GameStartSwaggerDoc
    @PostMapping("/start/{roomId}")
    public ResponseEntity<GameStatusResponse> startGame(@PathVariable String roomId) {
        return ResponseEntity.ok(GameStatusResponse.toStringId(gameStartUseCase.startGame(UUID.fromString(roomId))));
    }

    @GameCurrentSwaggerDoc
    @GetMapping("/current/{roomId}")
    public ResponseEntity<GameStatusResponse> getCurrentGame(@PathVariable String roomId) {
        return ResponseEntity.ok(gameQueryUseCase.findCurrentGameByRoomId(UUID.fromString(roomId)));
    }
} 