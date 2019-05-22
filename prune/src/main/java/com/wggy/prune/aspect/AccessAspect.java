package com.wggy.prune.aspect;

import com.google.gson.Gson;
import com.wggy.prune.configuration.redis.RedisProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author ping
 * @create 2019-05-16 17:49
 **/
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "prune.redis", name = "open", havingValue = "true")
public class AccessAspect implements InitializingBean {

    private static final int MAX_PARAM_SIZE = 1000;

    @Autowired
    private RedisProvider redisProvider;

    @Autowired
    private Gson gson;

    @Pointcut("@annotation(com.wggy.prune.aspect.annotation.AccessLimit)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>AccessAspect<<<<<<<<<<<<<<<<<<<<<<");
    }
}
