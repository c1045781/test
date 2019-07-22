package com.gk.university.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
