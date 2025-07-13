package com.jybeomss1.wordbattle_backend.game.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import com.jybeomss1.wordbattle_backend.game.domain.Game;
import com.jybeomss1.wordbattle_backend.game.domain.GameUserScore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private int roundCount;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameUserScoreJpaEntity> userScores;

    public static GameJpaEntity fromDomain(Game game) {
        GameJpaEntity entity = GameJpaEntity.builder()
            .id(game.getId() != null ? game.getId() : null)
            .roomId(game.getRoomId())
            .roundCount(game.getRoundCount())
            .build();
        entity.userScores = game.getUserScores().stream()
            .map(score -> GameUserScoreJpaEntity.builder()
                .userId(score.getUserId())
                .score(score.getScore())
                .game(entity)
                .build())
            .toList();
        return entity;
    }

    public Game toDomain() {
        List<GameUserScore> scores = userScores.stream()
            .map(us -> new GameUserScore(us.getUserId(), us.getScore()))
            .toList();
        return Game.builder()
            .id(id != null ? id : null)
            .roomId(roomId)
            .roundCount(roundCount)
            .userScores(scores)
            .build();
    }

    private GameJpaEntity toEntity(Game game) {
        List<GameUserScoreJpaEntity> userScoreEntities = game.getUserScores().stream()
                .map(score -> GameUserScoreJpaEntity.builder()
                        .userId(score.getUserId())
                        .score(score.getScore())
                        .build())
                .collect(Collectors.toList());
        GameJpaEntity entity = GameJpaEntity.builder()
                .id(game.getId() != null ? game.getId() : null)
                .roomId(game.getRoomId())
                .roundCount(game.getRoundCount())
                .userScores(userScoreEntities)
                .build();
        // 양방향 연관관계 설정
        userScoreEntities.forEach(us -> us.setGame(entity));
        return entity;
    }
} 