package com.wggy.prune.configuration.redis;

import com.wggy.prune.configuration.properties.RedisSwitchProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wggy
 * @create 2019-05-22 10:33
 **/
@Slf4j
@Component
@ConditionalOnBean(RedisSwitchProperties.class)
@ConditionalOnProperty(prefix = "prune.redis", name = "open", havingValue = "true")
public class PruneRedisSerializer implements RedisSerializer<String>, InitializingBean {
    private final Charset charset;
    private static final String SEP = ":";

    @Autowired
    private RedisSwitchProperties redisSwitchProperties;

    public PruneRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public PruneRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Nullable
    @Override
    public byte[] serialize(@Nullable String string) throws SerializationException {
        String keyPrefix = redisSwitchProperties.getPrefix();
        String key = keyPrefix + SEP +  string;
        byte[] bytes = key.getBytes(charset);
        log.info("key:{},getBytes:{}", key, bytes);
        return bytes;
    }

    @Nullable
    @Override
    public String deserialize(@Nullable byte[] bytes) throws SerializationException {
        String keyPrefix = redisSwitchProperties.getPrefix();
        String saveKey = new String(bytes, charset);
        int indexOf = saveKey.indexOf(keyPrefix);
        if (indexOf > 0) {
            log.info("key：【】缺少前缀", saveKey);
        } else {
            saveKey = saveKey.substring(indexOf);
        }
        return saveKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>PruneRedisSerializer<<<<<<<<<<<<<<<<<<<<<<");
    }
}
