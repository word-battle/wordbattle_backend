package com.jybeomss1.wordbattle_backend.quiz.domain.dto;

import com.jybeomss1.wordbattle_backend.quiz.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResponse {
    private String quizId;
    private String gameId;
    private String question;
    private String answer;
    private String creatorUserId;

    public static QuizResponse from(Quiz quiz) {
        return QuizResponse.builder()
                .quizId(quiz.getId().toString())
                .gameId(quiz.getGameId().toString())
                .question(quiz.getQuestion())
                .answer(quiz.getAnswer())
                .creatorUserId(quiz.getCreatorUserId().toString())
                .build();
    }

    public static QuizResponse toStringId(QuizResponse response) {
        return QuizResponse.builder()
                .quizId(response.getQuizId())
                .gameId(response.getGameId())
                .question(response.getQuestion())
                .answer(response.getAnswer())
                .creatorUserId(response.getCreatorUserId())
                .build();
    }
} 