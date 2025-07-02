package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

/**
 * 방에 속한 유저(RoomUser)의 JPA 엔티티
 */
@Entity
@Table(name = "room_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomUserJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomJpaEntity room;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private int score;
} 