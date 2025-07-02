package com.jybeomss1.wordbattle_backend.quiz.domain.dto.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizSubmitMessage {
    private String roomId;
    private String question;
    private String answer;
    private String userId;
    private String userName;
} 