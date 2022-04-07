package ru.sargassov.mcuserweb.exceptions;

public class Notice { //адаптивный класс обертка для реализации ответов фронту в виде ResponceEntity
    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notice() {
    }

    public Notice(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
