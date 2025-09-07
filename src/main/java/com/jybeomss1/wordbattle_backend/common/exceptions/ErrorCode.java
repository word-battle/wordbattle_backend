package com.jybeomss1.wordbattle_backend.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    REVOKED_TOKEN(HttpStatus.FORBIDDEN, "이미 만료된 토큰입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 방입니다."),
    ROOM_FULL(HttpStatus.BAD_REQUEST, "방 인원이 가득 찼습니다."),
    ROOM_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호를 입력해야 합니다."),
    ROOM_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게임입니다."),
    GAME_ALREADY_FINISHED(HttpStatus.BAD_REQUEST, "이미 모든 라운드가 종료된 게임입니다."),
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 퀴즈입니다."),
    ROOM_LIST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "방 리스트를 불러올 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
