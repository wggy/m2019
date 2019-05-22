package com.wggy.prune.interceptor;

import com.google.gson.Gson;
import com.wggy.prune.api.anotation.ApiAuth;
import com.wggy.prune.configuration.properties.AppProperties;
import com.wggy.prune.exception.BaseExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @author ping
 * API接口调用拦截器
 * @create 2019-04-24 16:59
 **/
@Slf4j
@Component
public class ApiRequestInterceptor extends HandlerInterceptorAdapter {

    public static final String API_PREFIX = "/api";
    private static final String AJAX_KEY = "x-requested-with";
    private static final String AJAX_VAL = "XMLHttpRequest";
    private static final long API_TIMEOUT = 600;

    @Autowired
    private AppProperties appProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ApiAuth apiAuth;
        if (handler instanceof HandlerMethod) {
            apiAuth = ((HandlerMethod) handler).getMethodAnnotation(ApiAuth.class);
        } else {
            return true;
        }

        if (apiAuth == null) {
            return true;
        }

        if (!isAjax(request)) {
            outPrint(response, BaseExceptionCode.ERROR_REQUEST);
            return false;
        }

        String uri = request.getRequestURI();
        if (!(StringUtils.isNotBlank(uri) && uri.startsWith(API_PREFIX))) {
            log.error("请求路径：{}，不符合接口前缀/api", uri);
            return false;
        }
        String appKey = getParam(request, "appKey");
        if (StringUtils.isBlank(appKey)) {
            outPrint(response, BaseExceptionCode.ERROR_APP_KEY);
            return false;
        }
        String reqTime = getParam(request, "time");
        long currTime = System.currentTimeMillis() / 1000;
        if (StringUtils.isBlank(reqTime) || Math.abs(currTime - Long.parseLong(reqTime)) > API_TIMEOUT) {
            outPrint(response, BaseExceptionCode.ERROR_EXPIRED);
            return false;
        }
        String sign = getParam(request, "sign");
        if (StringUtils.isBlank(sign)) {
            outPrint(response, BaseExceptionCode.ERROR_SIGN);
            return false;
        }

        String appSecret = appProperties.getSecretByKey(appKey);
        if (StringUtils.isBlank(appSecret)) {
            outPrint(response, BaseExceptionCode.ERROR_APP_KEY);
            return false;
        }

        ArrayList<String> keys = Collections.list(request.getParameterNames());
        keys.remove("sign");
        String[] keyArray = keys.toArray(new String[keys.size()]);
        Arrays.sort(keyArray);

        StringBuilder sb = new StringBuilder();
        for (String key : keyArray) {
            sb.append(key).append("=").append(getParam(request, key));
        }
        sb.append(appSecret);

        String signVal = DigestUtils.sha1Hex(sb.toString());
        if (!sign.equals(signVal)) {
            outPrint(response, BaseExceptionCode.ERROR_SIGN);
            return false;
        }
        return true;
    }

    private String getParam(HttpServletRequest request, String paramName) {
        String val = request.getHeader(paramName);
        if (StringUtils.isBlank(val)) {
            val = request.getParameter(paramName);
        }

        return val;
    }

    private boolean isAjax(HttpServletRequest request) {
        if (request.getHeader(AJAX_KEY) != null && request.getHeader(AJAX_KEY).equalsIgnoreCase(AJAX_VAL)) {
            return true;
        }
        return false;
    }

    private void outPrint(HttpServletResponse response, BaseExceptionCode exceptionCode) throws IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> rt = new HashMap<>();
        rt.put("code", exceptionCode.getCode());
        rt.put("msg", exceptionCode.getMsg());
        String json = new Gson().toJson(rt);
        OutputStream os = response.getOutputStream();
        os.write(json.getBytes());
    }


}
