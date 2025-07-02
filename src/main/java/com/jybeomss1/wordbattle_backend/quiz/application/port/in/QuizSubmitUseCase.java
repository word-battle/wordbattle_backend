package com.jybeomss1.wordbattle_backend.quiz.application.port.in;

import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizResponse;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizSubmitRequest;

import java.util.UUID;

public interface QuizSubmitUseCase {
    QuizResponse submitQuiz(QuizSubmitRequest request, UUID userId);
}
