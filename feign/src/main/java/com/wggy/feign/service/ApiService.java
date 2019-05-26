package com.wggy.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "eureka-client", fallback = ApiServiceError.class)
public interface ApiService {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    String index();
}
