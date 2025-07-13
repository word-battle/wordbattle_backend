package com.jybeomss1.wordbattle_backend.quiz.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.game.application.port.out.GamePort;
import com.jybeomss1.wordbattle_backend.game.domain.Game;
import com.jybeomss1.wordbattle_backend.quiz.application.port.in.QuizSubmitUseCase;
import com.jybeomss1.wordbattle_backend.quiz.application.port.out.QuizPort;
import com.jybeomss1.wordbattle_backend.quiz.domain.Quiz;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizResponse;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizSubmitRequest;
import com.jybeomss1.wordbattle_backend.room.application.port.out.RoomPort;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService implements QuizSubmitUseCase {
    private final GamePort gamePort;
    private final QuizPort quizPort;
    private final RoomPort roomPort;

    @Override
    @Transactional
    public QuizResponse submitQuiz(QuizSubmitRequest request, UUID userId) {
        // 1. 게임 조회
        UUID gameId = UUID.fromString(request.getGameId());
        Game game = gamePort.findById(gameId)
                .orElseThrow(() -> new BaseException(ErrorCode.GAME_NOT_FOUND));

        // 2. 현재 문제 번호 = QuizRepository에서 gameId로 count
        int currentRound = quizPort.countByGameId(gameId) + 1;

        // 3. 게임 종료 여부 판단 및 예외 처리
        Room room = roomPort.findById(game.getRoomId())
            .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        if (currentRound > room.getRoundCount()) {
            throw new BaseException(ErrorCode.GAME_ALREADY_FINISHED);
        }

        // 4. Quiz 생성 (빌더 내부 메서드 사용)
        Quiz quiz = Quiz.of(gameId, currentRound, request.getQuestion(), request.getAnswer(), userId);

        // 5. 저장
        Quiz saved = quizPort.save(quiz);

        // 6. 응답 반환
        return QuizResponse.from(saved);
    }
} 