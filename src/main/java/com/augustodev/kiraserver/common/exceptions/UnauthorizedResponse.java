package com.augustodev.kiraserver.common.exceptions;

public class UnauthorizedResponse {
    private int status;
    private String message;

    public UnauthorizedResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public UnauthorizedResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UnauthorizedResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
