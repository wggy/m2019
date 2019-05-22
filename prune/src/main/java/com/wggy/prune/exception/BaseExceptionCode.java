package com.wggy.prune.exception;
import lombok.Getter;

/**
 * @author ping
 * @create 2019-05-15 17:46
 **/
@Getter
public enum BaseExceptionCode {
    ERROR_APP_KEY(1001, "错误的应用key"),
    ERROR_EXPIRED(1002, "接口请求超时"),
    ERROR_SIGN(1003, "错误的数字签名"),
    ERROR_REQUEST(1004, "错误的请求")
    ;


    private int code;
    private String msg;
    BaseExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
