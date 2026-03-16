package com.WM.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    //1：成功 0：失败
    private Integer code;

    //异常信息
    private String msg;

    //数据
    private T data;

    //成功（不传数据）
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    //成功（传数据）
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    //失败（异常信息）
    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
