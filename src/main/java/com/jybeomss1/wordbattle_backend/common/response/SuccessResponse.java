package com.jybeomss1.wordbattle_backend.common.response;

import lombok.Getter;

@Getter
public class SuccessResponse {
    private String code;
    private String message;

    public SuccessResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}