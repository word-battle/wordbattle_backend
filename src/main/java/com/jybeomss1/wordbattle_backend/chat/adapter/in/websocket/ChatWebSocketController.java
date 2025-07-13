package com.jybeomss1.wordbattle_backend.chat.adapter.in.websocket;

import com.jybeomss1.wordbattle_backend.chat.application.port.ChatAnswerCheckUseCase;
import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatAnswerCheckUseCase chatAnswerCheckUseCase;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage message) {
        return chatAnswerCheckUseCase.checkAnswerAndHandle(roomId, message);
    }
} 