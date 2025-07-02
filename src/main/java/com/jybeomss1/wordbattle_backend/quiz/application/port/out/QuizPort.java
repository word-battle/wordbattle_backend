package com.jybeomss1.wordbattle_backend.quiz.application.port.out;

import com.jybeomss1.wordbattle_backend.quiz.domain.Quiz;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface QuizPort {
    Quiz save(Quiz quiz);
    Optional<Quiz> findById(UUID quizId);
    List<Quiz> findByGameId(UUID gameId);
} 