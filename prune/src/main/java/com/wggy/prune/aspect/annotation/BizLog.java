package com.wggy.prune.aspect.annotation;

import java.lang.annotation.*;


/**
 * @author ping
 * @create 2019-05-16 17:50
 * 系统操作日志标记
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizLog {
    String value() default "";
}
