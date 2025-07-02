package com.jybeomss1.wordbattle_backend.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * 퀴즈(Quiz) 도메인 모델
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private UUID id;
    private UUID gameId;
    private String question;
    private String answer;
    private UUID creatorUserId;

    public static Quiz of(UUID gameId, String question, String answer, UUID creatorUserId) {
        return Quiz.builder()
                .gameId(gameId)
                .question(question)
                .answer(answer)
                .creatorUserId(creatorUserId)
                .build();
    }
} 