package com.wggy.eureka.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *  create by wggy on 2019/5/28 23:21 
 **/
@RestController
@RefreshScope
public class HelloController {
    @Value("${server.port}")
    private int port;

    @RequestMapping("index")
    public String index() {
        return "Hi, your port is " + port;
    }
}
