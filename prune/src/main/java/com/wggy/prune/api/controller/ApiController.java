package com.wggy.prune.api.controller;

import com.wggy.prune.api.service.ApiService;
import com.wggy.prune.common.R;
import com.wggy.prune.configuration.redis.RedisProvider;
import com.wggy.prune.rbac.service.BizLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

/**
 * @author ping
 * @create 2019-05-16 17:39
 **/
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

//    @Autowired
//    private RedisProvider redisProvider;

    @Autowired
    private BizLogService bizLogService;
    @RequestMapping("test")
    public R test(Long id) {
        return R.ok().put("data", bizLogService.getById(id));
    }
}
