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
@Operation(summary = "로그인", description = "기본 로그인")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "로그인 성공 예시 응답",
                                value = "토큰 발급"
                        )
                )
        )
//        ,
//        @ApiResponse(
//                responseCode = "400",
//                description = "로그인 실패",
//                content = @Content(
//                        mediaType = "application/json",
//                        examples = @ExampleObject(
//                                name = "로그인 실패 예시 응답",
//                                value = "{\"code\":\"LOGIN_FAIL\",\"message\":\"로그인 실패\"}"
//                        )
//                )
//        )
})
@JwtAuth
public @interface UserLoginSwaggerDoc {
}
