package com.zbf.zbfapibackend.exception;

import com.zbf.zbfapibackend.common.ErrorCode;

/**
 * @author zbf
 */
public class ThrowUtils {
    
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }
    
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    public static void throwIf(boolean condition, ErrorCode errorCode, String mes) {
        throwIf(condition, new BusinessException(errorCode, mes));
    }
}
