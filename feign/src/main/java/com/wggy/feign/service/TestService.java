package com.wggy.feign.service;

import org.springframework.stereotype.Component;

@Component
public class TestService {

    int cnt = 0;
    public int getCnt() {
        return ++cnt;
    }
}
