package com.jybeomss1.wordbattle_backend.chat.application.port;

import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;

public interface ChatAnswerCheckUseCase {
    ChatMessage checkAnswerAndHandle(String roomId, ChatMessage message);
} 