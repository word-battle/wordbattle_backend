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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService implements QuizSubmitUseCase {
    private final GamePort gamePort;
    private final QuizPort quizPort;

    @Override
    @Transactional
    public QuizResponse submitQuiz(QuizSubmitRequest request, UUID userid) {
        // 1. 게임 조회
        UUID gameId = UUID.fromString(request.getGameId());
        Game game = gamePort.findById(gameId)
                .orElseThrow(() -> new BaseException(ErrorCode.GAME_NOT_FOUND));
        // 1-1. currentQuizIndex 1 증가 및 저장
        Game updatedGame = game.increaseQuizIndex();
        gamePort.save(updatedGame);
        // 2. Quiz 생성
        Quiz quiz = Quiz.of(gameId, request.getQuestion(), request.getAnswer(), userid);
        // 3. 저장
        Quiz saved = quizPort.save(quiz);
        // 4. 응답 반환
        return QuizResponse.from(saved);
    }
} 