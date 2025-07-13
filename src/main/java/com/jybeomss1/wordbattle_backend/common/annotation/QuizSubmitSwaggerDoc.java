package com.jybeomss1.wordbattle_backend.common.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "퀴즈 제출", description = "퀴즈를 제출합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "퀴즈 제출 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "퀴즈 제출 성공 예시",
                                value = "{\"quizId\":\"quiz-uuid\",\"gameId\":\"game-uuid\",\"question\":\"문제 내용\",\"answer\":\"정답\",\"creatorUserId\":\"user-uuid\"}"
                        )
                )
        )
//        ,
//        @ApiResponse(
//                responseCode = "400",
//                description = "퀴즈 제출 실패",
//                content = @Content(
//                        mediaType = "application/json",
//                        examples = @ExampleObject(
//                                name = "퀴즈 제출 실패 예시",
//                                value = "{\"code\":\"QUIZ_ALREADY_EXISTS\",\"message\":\"퀴즈 제출 실패: 이미 출제된 문제입니다.\"}"
//                        )
//                )
//        )
})
@JwtAuth
public @interface QuizSubmitSwaggerDoc {} 