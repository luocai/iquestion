package com.iquestion.common;

public class Result<T>{

    private Integer resultCode;

    private String message;

    private T data;

    public Result(Integer resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public Result(Integer resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public Result(Integer resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public Integer getresultCode() {
        return resultCode;
    }

    public void setresultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
