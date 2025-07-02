package com.jybeomss1.wordbattle_backend.quiz.service;

import com.jybeomss1.wordbattle_backend.quiz.adapter.in.web.QuizController;
import com.jybeomss1.wordbattle_backend.quiz.application.port.in.QuizSubmitUseCase;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizResponse;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizSubmitRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
    @InjectMocks
    private QuizController quizController;
    @Mock
    private QuizSubmitUseCase quizSubmitUseCase;

    @Test
    @DisplayName("퀴즈 제출 성공")
    void submitQuiz_success() {
        QuizSubmitRequest request = QuizSubmitRequest.builder()
                .gameId(UUID.randomUUID().toString())
                .question("문제?")
                .answer("정답")
                .build();
        CustomUserDetails userDetails = Mockito.mock(CustomUserDetails.class);
        Mockito.when(userDetails.getUserId()).thenReturn(UUID.randomUUID());
        QuizResponse responseDto = QuizResponse.builder().quizId(UUID.randomUUID().toString()).gameId(request.getGameId()).question("문제?").answer("정답").creatorUserId(UUID.randomUUID().toString()).build();
        Mockito.when(quizSubmitUseCase.submitQuiz(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDto);
        ResponseEntity<QuizResponse> response = quizController.submitQuiz(request, userDetails);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("문제?", response.getBody().getQuestion());
    }
} 