package com.wggy.prune.exception;

/**
 * @author ping
 * @create 2019-05-15 17:42
 **/

public class BaseException extends RuntimeException {
    private final Integer code;
    private final String message;

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
