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
@Operation(summary = "방 리스트 조회", description = "모든 방의 리스트를 조회합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "방 리스트 조회 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "방 리스트 조회 성공 예시",
                                value = "[{\"roomId\":\"123e4567-e89b-12d3-a456-426614174000\",\"roomName\":\"testRoom\",\"currentUsers\":2,\"quizCount\":5,\"hasPassword\":false}]"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "방 리스트 조회 실패",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "방 리스트 조회 실패 예시",
                                value = "{\"code\":\"ROOM_LIST_ERROR\",\"message\":\"방 리스트를 불러올 수 없습니다.\"}"
                        )
                )
        )
})
public @interface RoomListSwaggerDoc {} 