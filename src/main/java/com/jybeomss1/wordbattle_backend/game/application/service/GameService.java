package com.jybeomss1.wordbattle_backend.game.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameActionUseCase;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameStartUseCase;
import com.jybeomss1.wordbattle_backend.game.application.port.out.GamePort;
import com.jybeomss1.wordbattle_backend.game.domain.Game;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;
import com.jybeomss1.wordbattle_backend.room.application.port.out.RoomPort;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 게임 관련 비즈니스 로직 서비스
 */
@Service
@RequiredArgsConstructor
public class GameService implements GameStartUseCase, GameActionUseCase {
    private final GamePort gamePort;
    private final RoomPort roomPort;

    @Override
    @Transactional
    public GameStatusResponse startGame(UUID roomId) {
        // 1. RoomPort를 통해 방 조회
        Room room = roomPort.findById(roomId)
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        // 2. gameCount 1 증가
        Room roomWithIncreasedGameCount = room.increaseGameCount();
        // 3. Room 상태 변경(IN_PROGRESS)
        Room updatedRoom = Room.withStatus(roomWithIncreasedGameCount, GameStatus.IN_PROGRESS);
        roomPort.save(updatedRoom);
        // 4. Game 생성 (Room만 참조)
        Game game = Game.of(updatedRoom);
        Game saved = gamePort.save(game);
        // 5. 응답 변환
        return GameStatusResponse.of(saved.getId(), saved.getCurrentQuizIndex());
    }
} 