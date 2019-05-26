package com.wggy.feign.service;

import org.springframework.stereotype.Component;

@Component
public class ApiServiceError implements ApiService {
    int cnt = 0;
    @Override
    public String index() {
        return "服务发生故障！";
    }
}
