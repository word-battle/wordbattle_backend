package com.jybeomss1.wordbattle_backend.game.domain.dto.websocket;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GameStatusMessage {
    private String roomId;
    private List<String> userNames;
    private int currentQuizIndex;
    private String status;
    private String lastQuestion;
    private String lastAnswerUser;
    private boolean correct;
} 