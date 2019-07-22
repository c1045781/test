package com.gk.university.exception;

public enum CustomizeErrorCode {
    QUESTION_NOT_FOUND("文章不存在");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
