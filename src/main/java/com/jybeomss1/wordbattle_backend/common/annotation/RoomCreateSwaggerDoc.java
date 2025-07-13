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
@Operation(summary = "방 생성", description = "새로운 방을 생성합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "방 생성 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "방 생성 성공 예시",
                                value = "{\"roomId\":\"123e4567-e89b-12d3-a456-426614174000\",\"roomName\":\"testRoom\",\"joinCode\":\"ABC123\",\"hasPassword\":false,\"roundCount\":5}"
                        )
                )
        )
//        ,
//        @ApiResponse(
//                responseCode = "400",
//                description = "방 생성 실패",
//                content = @Content(
//                        mediaType = "application/json",
//                        examples = @ExampleObject(
//                                name = "방 생성 실패 예시",
//                                value = "{\"code\":\"ROOM_NAME_EXISTS\",\"message\":\"이미 존재하는 방 이름입니다.\"}"
//                        )
//                )
//        )
})
@JwtAuth
public @interface RoomCreateSwaggerDoc {} 