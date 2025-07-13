package com.jybeomss1.wordbattle_backend.game.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameQueryUseCase;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameScoreUseCase;
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

@Service
@RequiredArgsConstructor
public class GameService implements GameStartUseCase, GameQueryUseCase, GameScoreUseCase {
    private final GamePort gamePort;
    private final RoomPort roomPort;

    @Override
    @Transactional
    public GameStatusResponse startGame(UUID roomId) {
        // 1. RoomPort를 통해 방 조회
        Room room = getRoom(roomId);

        Room updatedRoom = Room.withStatus(room, GameStatus.IN_PROGRESS);

        roomPort.save(updatedRoom);

        Game game = Game.of(updatedRoom);
        Game saved = gamePort.save(game);
        return GameStatusResponse.of(saved.getId(), saved.getRoundCount());
    }

    @Override
    @Transactional
    public GameStatusResponse findCurrentGameByRoomId(UUID roomId) {
        Game game = gamePort.findCurrentGameByRoomId(roomId);
        if (game == null) {
            throw new BaseException(ErrorCode.GAME_NOT_FOUND);
        }
        Room room = getRoom(roomId);
        return GameStatusResponse.builder()
                .roomId(room.getId().toString())
                .users(room.getUsers())
                .gameId(game.getId().toString())
                .currentRoundCount(game.getRoundCount())
                .status(room.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void addScore(UUID gameId, String userId, int score) {
        Game game = gamePort.findById(gameId)
                .orElseThrow(() -> new BaseException(ErrorCode.GAME_NOT_FOUND));
        game.addScoreToUser(UUID.fromString(userId), score);
        gamePort.save(game);
    }

    private Room getRoom(UUID roomId) {
        return roomPort.findById(roomId)
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
    }
} 