package com.jybeomss1.wordbattle_backend.chat.application.port;

import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;

public interface ChatSavePort {
    void save(ChatMessage message);
} 