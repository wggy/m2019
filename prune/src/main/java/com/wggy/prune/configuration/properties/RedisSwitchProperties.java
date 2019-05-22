package com.wggy.prune.configuration.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wggy
 * @create 2019-05-22 11:15
 **/
@Data
@Slf4j
@Component
@ConfigurationProperties("prune.redis")
public class RedisSwitchProperties implements InitializingBean {
    private Boolean open;
    private String prefix;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("open: {}, prefix: {}", open, prefix);
    }
}
