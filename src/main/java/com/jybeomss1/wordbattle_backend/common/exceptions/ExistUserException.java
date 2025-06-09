package com.jybeomss1.wordbattle_backend.common.exceptions;

public class ExistUserException extends BaseException {
    public ExistUserException() {
        super(ErrorCode.EXIST_USER);
    }
}
