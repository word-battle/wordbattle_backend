package com.jybeomss1.wordbattle_backend.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EXIST_USER(HttpStatus.BAD_REQUEST, "USER_001", "해당 사용자는 이미 존재합니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "USER_002", "해당 사용자를 찾을 수 없습니다."),
    REVOKED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "이미 로그아웃 된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
