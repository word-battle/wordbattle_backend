package com.jybeomss1.wordbattle_backend.game.service;

import com.jybeomss1.wordbattle_backend.game.adapter.in.web.GameController;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameStartUseCase;
import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @InjectMocks
    private GameController gameController;
    @Mock
    private GameStartUseCase gameStartUseCase;

    @Test
    @DisplayName("게임 시작 성공")
    void startGame_success() {
        String roomId = UUID.randomUUID().toString();
        GameStatusResponse responseDto = GameStatusResponse.builder().gameId(UUID.randomUUID().toString()).currentQuizIndex(0).build();
        Mockito.when(gameStartUseCase.startGame(ArgumentMatchers.any())).thenReturn(responseDto);
        ResponseEntity<GameStatusResponse> response = gameController.startGame(roomId);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getCurrentQuizIndex());
    }
} 