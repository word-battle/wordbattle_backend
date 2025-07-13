package com.jybeomss1.wordbattle_backend.quiz.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmitRequest {
    private String gameId;
    private String question;
    private String answer;
} 