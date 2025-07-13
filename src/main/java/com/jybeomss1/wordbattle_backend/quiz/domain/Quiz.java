package com.jybeomss1.wordbattle_backend.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

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
    private int roundNumber;

    public static Quiz of(UUID gameId, int roundNumber, String question, String answer, UUID userId) {
        return Quiz.builder()
            .gameId(gameId)
            .roundNumber(roundNumber)
            .question(question)
            .answer(answer)
            .creatorUserId(userId)
            .build();
    }
} 