package com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String roomId;
    private String sender;
    private String message;
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
} 