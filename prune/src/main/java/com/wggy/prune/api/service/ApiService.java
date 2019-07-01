package com.wggy.prune.api.service;

import org.springframework.cache.annotation.Cacheable;

public interface ApiService {

    @Cacheable(value="sys_logs", key="#id")
    String test(String key);
}
