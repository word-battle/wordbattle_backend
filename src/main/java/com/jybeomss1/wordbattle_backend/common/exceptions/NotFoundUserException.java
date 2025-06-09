package com.jybeomss1.wordbattle_backend.common.exceptions;

public class NotFoundUserException extends BaseException {
  public NotFoundUserException() {
    super(ErrorCode.NOT_FOUND_USER);
  }
}
