package com.jybeomss1.wordbattle_backend.common.exceptions;

public class InvalidatedTokenException extends BaseException {
    public InvalidatedTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
