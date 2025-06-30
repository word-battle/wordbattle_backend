package com.jybeomss1.wordbattle_backend.common.exceptions;

public class RevokedTokenException extends BaseException {
    public RevokedTokenException() {
        super(ErrorCode.REVOKED_TOKEN);
    }
}
