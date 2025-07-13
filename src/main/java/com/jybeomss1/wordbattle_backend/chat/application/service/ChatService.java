package com.jybeomss1.wordbattle_backend.chat.application.service;

import com.jybeomss1.wordbattle_backend.chat.application.port.ChatAnswerCheckUseCase;
import com.jybeomss1.wordbattle_backend.chat.application.port.ChatSavePort;
import com.jybeomss1.wordbattle_backend.chat.application.port.ChatSaveUseCase;
import com.jybeomss1.wordbattle_backend.chat.domain.dto.websocket.ChatMessage;
import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.game.application.port.in.GameScoreUseCase;
import com.jybeomss1.wordbattle_backend.game.application.port.out.GamePort;
import com.jybeomss1.wordbattle_backend.game.domain.Game;
import com.jybeomss1.wordbattle_backend.quiz.application.port.out.QuizPort;
import com.jybeomss1.wordbattle_backend.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService implements ChatSaveUseCase, ChatAnswerCheckUseCase {
    private final ChatSavePort chatSavePort;
    private final GameScoreUseCase gameScoreUseCase;
    private final GamePort gamePort;
    private final QuizPort quizPort;

    public void saveMessage(String roomId, ChatMessage message) {
        chatSavePort.save(message);
    }

    @Override
    public ChatMessage checkAnswerAndHandle(String roomId, ChatMessage message) {
        Game game = gamePort.findCurrentGameByRoomId(java.util.UUID.fromString(roomId));
        if (game == null) throw new BaseException(ErrorCode.GAME_NOT_FOUND);
        int round = game.getRoundCount();
        Quiz quiz = quizPort.findByGameIdAndRoundNumber(game.getId(), round)
                .orElseThrow(() -> new BaseException(ErrorCode.QUIZ_NOT_FOUND));

        saveMessage(roomId, message);

        // 정답 체크
        if (quiz.getAnswer().equals(message.getMessage())) {
            gameScoreUseCase.addScore(game.getId(), message.getSender(), 10);
            return ChatMessage.builder()
                    .roomId(message.getRoomId())
                    .sender("SYSTEM")
                    .message(message.getSender() + "님이 정답을 맞췄습니다! +10점")
                    .type(ChatMessage.MessageType.CHAT)
                    .build();
        }
        // 오답이면 원본 메시지 반환
        return message;
    }
} 