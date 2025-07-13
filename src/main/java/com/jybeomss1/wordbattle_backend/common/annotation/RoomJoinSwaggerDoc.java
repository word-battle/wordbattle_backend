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
@Operation(summary = "방 참가", description = "방에 참가합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "방 참가 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "방 참가 성공 예시",
                                value = "{\"roomId\":\"123e4567-e89b-12d3-a456-426614174000\",\"roomName\":\"testRoom\",\"currentUsers\":2,\"hasPassword\":false,\"roundCount\":5}"
                        )
                )
        )
//        ,
//        @ApiResponse(
//                responseCode = "400",
//                description = "방 참가 실패",
//                content = @Content(
//                        mediaType = "application/json",
//                        examples = @ExampleObject(
//                                name = "방 참가 실패 예시",
//                                value = "{\"code\":\"ROOM_JOIN_FAIL\",\"message\":\"방 참가 실패\"}"
//                        )
//                )
//        )
})
@JwtAuth
public @interface RoomJoinSwaggerDoc {}