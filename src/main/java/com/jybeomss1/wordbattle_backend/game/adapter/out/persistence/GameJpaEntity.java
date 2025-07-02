package com.jybeomss1.wordbattle_backend.game.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.adapter.out.persistence.RoomJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

/**
 * 게임(Game)의 JPA 엔티티
 */
@Entity
@Table(name = "game")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomJpaEntity room;

    @Column(nullable = false)
    private int currentQuizIndex;

    public static GameJpaEntity fromDomain(com.jybeomss1.wordbattle_backend.game.domain.Game game, RoomJpaEntity roomEntity) {
        return GameJpaEntity.builder()
                .id(game.getId())
                .room(roomEntity)
                .currentQuizIndex(game.getCurrentQuizIndex())
                .build();
    }

    public com.jybeomss1.wordbattle_backend.game.domain.Game toDomain() {
        return com.jybeomss1.wordbattle_backend.game.domain.Game.builder()
                .id(this.getId())
                .room(this.getRoom() != null ? this.getRoom().toDomain() : null)
                .currentQuizIndex(this.getCurrentQuizIndex())
                .build();
    }
} 