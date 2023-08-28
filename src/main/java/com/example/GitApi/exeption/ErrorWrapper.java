package com.example.GitApi.exeption;

public class ErrorWrapper extends RuntimeException {

    private Integer status;

    public ErrorWrapper(Integer status, String message) {
        super(message);
        this.status = status;
    }
    public Integer getStatus() {
        return status;
    }
}
