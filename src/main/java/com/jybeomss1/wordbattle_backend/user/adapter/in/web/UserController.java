package com.jybeomss1.wordbattle_backend.user.adapter.in.web;

import com.jybeomss1.wordbattle_backend.common.annotation.UserJoinSwaggerDoc;
import com.jybeomss1.wordbattle_backend.common.annotation.UserLoginSwaggerDoc;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserJoinRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserLoginRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserJoinUseCase userJoinUseCase;
    private final UserLoginUseCase userLoginUseCase;

    @PostMapping("/join")
    @UserJoinSwaggerDoc
    public ResponseEntity<String> join(@RequestBody UserJoinRequest request) {
        userJoinUseCase.join(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    @UserLoginSwaggerDoc
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userLoginUseCase.login(request.getEmail(), request.getPassword()));
    }
}
