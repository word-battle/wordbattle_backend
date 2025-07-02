package com.jybeomss1.wordbattle_backend.quiz.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.quiz.application.port.out.QuizPort;
import com.jybeomss1.wordbattle_backend.game.adapter.out.persistence.GameJpaRepository;
import com.jybeomss1.wordbattle_backend.game.adapter.out.persistence.GameJpaEntity;
import com.jybeomss1.wordbattle_backend.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QuizJpaAdapter implements QuizPort {
    private final QuizRepository quizRepository;
    private final GameJpaRepository gameJpaRepository;

    @Override
    public Quiz save(Quiz quiz) {
        GameJpaEntity gameEntity = gameJpaRepository.findById(quiz.getGameId())
            .orElseThrow(() -> new com.jybeomss1.wordbattle_backend.common.exceptions.BaseException(
                com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode.GAME_NOT_FOUND));
        QuizJpaEntity entity = QuizJpaEntity.fromDomain(quiz, gameEntity);
        return quizRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Quiz> findById(UUID quizId) {
        return quizRepository.findById(quizId).map(QuizJpaEntity::toDomain);
    }

    @Override
    public List<Quiz> findByGameId(UUID gameId) {
        return quizRepository.findAllByGameId(gameId).stream()
            .map(QuizJpaEntity::toDomain)
            .toList();
    }
} 