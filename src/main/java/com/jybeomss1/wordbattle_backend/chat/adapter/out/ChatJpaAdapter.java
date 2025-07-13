package com.jybeomss1.wordbattle_backend.chat.adapter.out;

import com.jybeomss1.wordbattle_backend.chat.application.port.ChatSavePort;
import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatJpaAdapter implements ChatSavePort {
    private final ChatJpaRepository chatJpaRepository;
    @Autowired
    public ChatJpaAdapter(ChatJpaRepository chatJpaRepository) {
        this.chatJpaRepository = chatJpaRepository;
    }
    @Override
    public void save(ChatMessage message) {
        ChatJpaEntity entity = ChatJpaEntity.fromDomain(message);
        chatJpaRepository.save(entity);
    }
} 