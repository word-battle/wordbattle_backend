package com.jybeomss1.wordbattle_backend.game.adapter.in.websocket;

import com.jybeomss1.wordbattle_backend.quiz.domain.dto.websocket.QuizSubmitMessage;
import com.jybeomss1.wordbattle_backend.game.domain.dto.websocket.GameStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {
//    @MessageMapping("/quiz/submit")
//    @SendTo("/topic/game-status")
//    public GameStatusMessage submitQuiz(@Payload QuizSubmitMessage message) {
//        // TODO: 서비스 호출 및 게임 상태 갱신
//        return new GameStatusMessage();
//    }
} 