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
@Operation(summary = "회원가입", description = "기본 회원 가입")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "회원가입 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "회원가입 성공 예시 응답",
                                value = "\"success\""
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "회원가입 실패",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "회원가입 실패 예시 응답",
                                value = "\"해당 사용자는 이미 존재합니다.\""
                        )
                )
        )
})
public @interface UserJoinSwaggerDoc {
}
