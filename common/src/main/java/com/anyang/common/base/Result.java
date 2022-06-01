package com.anyang.common.base;

import lombok.Data;

@Data
public class Result<T> {

    private T data;
    private String errorMsg;
    private String code;
    private boolean success = false;


    public static <T> Result wrapSuccess(T data) {
        Result result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }


    public static <T> Result wrapError(String code, String errorMsg) {
        return wrapError(code, errorMsg, null);
    }

    public static <T> Result wrapError(String code, String errorMsg, T data) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setErrorMsg(errorMsg);
        result.setData(data);
        return result;
    }
}
