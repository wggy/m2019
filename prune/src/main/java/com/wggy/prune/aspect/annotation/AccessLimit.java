package com.wggy.prune.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author ping
 * @create 2019-05-16 17:50
 *
 * 接口调用频次限制标记
 *
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /***
     * 接口限制请求
     * 0：不限制
     * > 0
     * @return
     */
    int limit() default 0;
}
