package com.jybeomss1.wordbattle_backend.chat.adapter.out;

import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;
import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private String type;

    public static ChatJpaEntity fromDomain(ChatMessage message) {
        return ChatJpaEntity.builder()
                .roomId(message.getRoomId())
                .sender(message.getSender())
                .message(message.getMessage())
                .type(message.getType().name())
                .build();
    }
} 