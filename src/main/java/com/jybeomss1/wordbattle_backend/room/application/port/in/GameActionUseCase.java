// 파일을 src/main/java/com/jybeomss1/wordbattle_backend/game/application/port/in/GameActionUseCase.java로 이동

package com.jybeomss1.wordbattle_backend.room.application.port.in;

import com.jybeomss1.wordbattle_backend.game.domain.dto.GameStatusResponse;

/**
 * 게임 진행/행동 유스케이스 인터페이스
 * (퀴즈 제출, 정답 제출, 힌트 등)
 */
public interface GameActionUseCase {
    // 퀴즈 제출, 정답 제출, 힌트 등 메서드 시그니처 추가 예정
    GameStatusResponse submitQuiz(Object request); // TODO: 실제 DTO로 교체
    GameStatusResponse answerQuiz(Object request); // TODO: 실제 DTO로 교체
    GameStatusResponse giveHint(Object request);   // TODO: 실제 DTO로 교체
    GameStatusResponse getGameStatus(String roomId);
} 