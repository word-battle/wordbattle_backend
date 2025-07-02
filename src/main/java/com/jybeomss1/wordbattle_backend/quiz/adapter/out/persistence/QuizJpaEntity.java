package com.jybeomss1.wordbattle_backend.quiz.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import com.jybeomss1.wordbattle_backend.game.adapter.out.persistence.GameJpaEntity;
import com.jybeomss1.wordbattle_backend.quiz.domain.Quiz;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

/**
 * 퀴즈(Quiz)의 JPA 엔티티
 */
@Entity
@Table(name = "quiz")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QuizJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameJpaEntity game;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private UUID creatorUserId;

    public static QuizJpaEntity fromDomain(Quiz quiz, GameJpaEntity gameEntity) {
        return QuizJpaEntity.builder()
                .id(quiz.getId())
                .game(gameEntity)
                .question(quiz.getQuestion())
                .answer(quiz.getAnswer())
                .creatorUserId(quiz.getCreatorUserId())
                .build();
    }

    public Quiz toDomain() {
        return Quiz.builder()
                .id(this.getId())
                .gameId(this.getGame() != null ? this.getGame().getId() : null)
                .question(this.getQuestion())
                .answer(this.getAnswer())
                .creatorUserId(this.getCreatorUserId())
                .build();
    }
} 