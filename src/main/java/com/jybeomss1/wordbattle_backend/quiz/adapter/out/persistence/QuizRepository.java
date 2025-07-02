package com.jybeomss1.wordbattle_backend.quiz.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<QuizJpaEntity, UUID> {
    List<QuizJpaEntity> findAllByGameId(UUID gameId);
} 