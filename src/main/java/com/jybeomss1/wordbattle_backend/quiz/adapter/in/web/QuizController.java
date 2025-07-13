package com.jybeomss1.wordbattle_backend.quiz.adapter.in.web;

import com.jybeomss1.wordbattle_backend.common.annotation.QuizSubmitSwaggerDoc;
import com.jybeomss1.wordbattle_backend.quiz.application.port.in.QuizSubmitUseCase;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizResponse;
import com.jybeomss1.wordbattle_backend.quiz.domain.dto.QuizSubmitRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizSubmitUseCase quizSubmitUseCase;

    @QuizSubmitSwaggerDoc
    @PostMapping("/submit")
    public ResponseEntity<QuizResponse> submitQuiz(@RequestBody QuizSubmitRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        QuizResponse response = quizSubmitUseCase.submitQuiz(request, userDetails.getUserId());
        return ResponseEntity.ok(QuizResponse.toStringId(response));
    }
} 