package com.augustodev.kiraserver.common.exceptions;


public class BadRequestResponse {
    private int status;
    private String message;

    public BadRequestResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public BadRequestResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BadRequestResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
