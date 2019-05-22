package com.wggy.prune.api.service.impl;

import com.wggy.prune.api.service.ApiService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiServiceImpl implements ApiService {


    @Override
    public String test(String key) {
        return UUID.fromString(key).toString();
    }
}
