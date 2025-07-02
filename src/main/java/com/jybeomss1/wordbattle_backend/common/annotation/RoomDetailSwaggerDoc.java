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
@Operation(summary = "방 상세 조회", description = "특정 방의 상세 정보를 조회합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "방 상세 조회 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "방 상세 조회 성공 예시",
                                value = "{\"roomId\":\"123e4567-e89b-12d3-a456-426614174000\",\"roomName\":\"testRoom\",\"gameCount\":5,\"hasPassword\":false,\"users\":[{\"id\":\"1\",\"userId\":\"user-uuid\",\"nickname\":\"tester\",\"score\":0,\"isHost\":true}]}"
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "방 상세 조회 실패",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "방 상세 조회 실패 예시",
                                value = "{\"code\":\"ROOM_NOT_FOUND\",\"message\":\"방이 존재하지 않습니다.\"}"
                        )
                )
        )
})
public @interface RoomDetailSwaggerDoc {} 