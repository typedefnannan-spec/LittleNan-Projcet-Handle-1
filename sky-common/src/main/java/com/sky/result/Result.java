package com.sky.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    //1:Success 0:Failed
    private Integer code;

    //Error message
    private String msg;

    //Data
    private T data;

    //Success(No data)
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    //Success(Data)
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    //Error(Message)
    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
