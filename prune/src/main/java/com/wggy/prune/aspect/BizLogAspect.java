package com.wggy.prune.aspect;

import com.google.gson.Gson;
import com.wggy.prune.aspect.annotation.BizLog;
import com.wggy.prune.common.HttpContextUtils;
import com.wggy.prune.common.IPUtils;
import com.wggy.prune.rbac.entity.BizLogEntity;
import com.wggy.prune.rbac.service.BizLogService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ping
 * @create 2019-05-16 17:48
 **/
@Aspect
@Component
public class BizLogAspect {
    private static final int MAX_PARAM_SIZE = 1000;

    @Autowired
    private BizLogService bizLogService;

    @Autowired
    private Gson gson;

    @Pointcut("@annotation(com.wggy.prune.aspect.annotation.BizLog)")
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

    private void saveBizLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        BizLogEntity log = new BizLogEntity();
        BizLog buzLog = method.getAnnotation(BizLog.class);
        if (buzLog == null) {
            return;
        }
        log.setOperation(buzLog.value());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");

        try {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                List<Object> list = new ArrayList<>();
                for (Object obj : args) {
                    if (!(obj instanceof ServletRequest || obj instanceof ServletResponse)) {
                        list.add(obj);
                    }
                }
                String params = gson.toJson(list);
                if (StringUtils.isNotBlank(params) && params.length() >= MAX_PARAM_SIZE) {
                    log.setParams(params.substring(0, MAX_PARAM_SIZE));
                } else {
                    log.setParams(params);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        log.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = "";
        log.setUsername(username);

        log.setTime(time);
        log.setCreateDate(LocalDateTime.now());
        //保存系统日志
        bizLogService.saveLog(log);
    }
}
