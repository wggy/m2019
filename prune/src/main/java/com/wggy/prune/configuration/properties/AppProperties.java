package com.wggy.prune.configuration.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ping
 * @create 2019-05-15 18:01
 **/
@Slf4j
@Data
@Component
@ConfigurationProperties("prune")
public class AppProperties implements InitializingBean {

    private List<Map<String, AppInfo>> app;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("app: {}", app);
    }


    @Data
    public static class AppInfo {
        private String key;
        private String secret;
    }

    public String getSecretByKey(String key) {
        for (Map<String, AppInfo> map : app) {
            for (AppInfo appInfo : map.values()) {
                if (appInfo.getKey().equalsIgnoreCase(key)) {
                    return appInfo.getSecret();
                }
            }
        }
        return null;
    }
}
