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
@Operation(summary = "게임 시작", description = "게임을 시작합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "게임 시작 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "게임 시작 성공 예시",
                                value = "{\"roomId\":\"123e4567-e89b-12d3-a456-426614174000\",\"users\":[{\"id\":\"1\",\"userId\":\"user-uuid\",\"nickname\":\"tester\",\"score\":0,\"isHost\":true}],\"gameId\":\"game-uuid\",\"currentQuizIndex\":1,\"status\":\"IN_PROGRESS\"}"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "게임 시작 실패",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "게임 시작 실패 예시",
                                value = "{\"code\":\"GAME_NOT_FOUND\",\"message\":\"게임 시작 실패: 방이 존재하지 않습니다.\"}"
                        )
                )
        )
})
public @interface GameStartSwaggerDoc {} 