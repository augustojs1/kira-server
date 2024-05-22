package com.augustodev.kiraserver.common.exceptions;


public class ResourceNotFoundResponse {
    private int status;
    private String message;

    public ResourceNotFoundResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public ResourceNotFoundResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResourceNotFoundResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
