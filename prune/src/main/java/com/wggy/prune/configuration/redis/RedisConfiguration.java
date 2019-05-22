package com.wggy.prune.configuration.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * @author wggy
 * @create 2019-05-17 16:11
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "prune.redis", name = "open", havingValue = "true")
public class RedisConfiguration extends CachingConfigurerSupport implements InitializingBean {
    @Autowired
    private RedisConnectionFactory factory;
    @Autowired
    private PruneRedisSerializer pruneRedisSerializer;

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuffer sb = new StringBuffer();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        log.info(">>>>>>>>>>>>>>>>>>加载RedisTemplate<<<<<<<<<<<<<<<<<<<<<<");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(pruneRedisSerializer);
        redisTemplate.setHashKeySerializer(pruneRedisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new ObjectMapper());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>RedisConfiguration<<<<<<<<<<<<<<<<<<<<<<");
    }
}
