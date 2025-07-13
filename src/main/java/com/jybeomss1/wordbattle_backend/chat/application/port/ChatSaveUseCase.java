package com.jybeomss1.wordbattle_backend.chat.application.port;

import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;

public interface ChatSaveUseCase {
    void saveMessage(String roomId, ChatMessage message);
} 