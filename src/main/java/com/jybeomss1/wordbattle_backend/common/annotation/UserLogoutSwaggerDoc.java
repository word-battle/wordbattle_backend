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
@Operation(summary = "로그아웃", description = "로그아웃 처리")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "로그아웃 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "로그아웃 성공 예시",
                                value = "{\"code\":\"SUCCESS\",\"message\":\"로그아웃이 성공적으로 처리되었습니다.\"}"
                        )
                )
        )
})
@JwtAuth
public @interface UserLogoutSwaggerDoc {} 