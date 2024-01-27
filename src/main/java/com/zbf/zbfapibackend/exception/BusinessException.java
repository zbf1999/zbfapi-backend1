package com.zbf.zbfapibackend.exception;

import com.zbf.zbfapibackend.common.ErrorCode;

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException{

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
