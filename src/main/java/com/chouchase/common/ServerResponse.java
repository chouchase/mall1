package com.chouchase.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> {
    private final int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T date) {
        this.status = status;
        this.data = date;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }



    public static <T> ServerResponse<T> createSuccessResponseByMsg(String msg) {
        return new ServerResponse<>(ResponseStatus.SUCCESS, msg);
    }

    public static <T> ServerResponse<T> createSuccessResponseByData(T data) {
        return new ServerResponse<>(ResponseStatus.SUCCESS, data);
    }

    public static <T> ServerResponse<T> createSuccessResponseByMsgAndData(String msg, T data) {
        return new ServerResponse<>(ResponseStatus.SUCCESS, msg, data);
    }



    public static <T> ServerResponse<T> createFailResponseByMsg(String msg) {
        return new ServerResponse<>(ResponseStatus.FAIL, msg);
    }

    public static <T> ServerResponse<T> createFailResponseByData(T data) {
        return new ServerResponse<>(ResponseStatus.FAIL, data);
    }

    public static <T> ServerResponse<T> createFailResponseByMsgAndData(String msg, T data) {
        return new ServerResponse<>(ResponseStatus.FAIL, msg, data);
    }
    public static <T> ServerResponse<T> createIllegalArgsResponse(){
        return new ServerResponse<>(ResponseStatus.ILLEGALARGUMENT,"参数错误");
    }
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseStatus.SUCCESS;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

}
