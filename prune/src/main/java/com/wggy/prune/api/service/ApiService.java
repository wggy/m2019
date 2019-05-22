package com.wggy.prune.api.service;

import org.springframework.cache.annotation.Cacheable;

public interface ApiService {

    @Cacheable(key="'user_'+#key",value="'user'+#key")
    String test(String key);
}
