package com.wggy.prune.common;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author wggy
 * @create 2019-05-16 18:07
 **/
@Slf4j
public class IPUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if ("127.0.0.1".equals(ip)) {
                    InetAddress localHost = InetAddress.getLocalHost();
                    ip = localHost.getHostAddress();
                }
            }
            if (!StringUtils.isEmpty(ip) && ip.length() > 15) {
                if (ip.contains(",")) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }

        return ip;
    }
}
