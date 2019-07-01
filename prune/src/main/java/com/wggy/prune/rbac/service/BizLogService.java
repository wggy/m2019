package com.wggy.prune.rbac.service;

import com.wggy.prune.rbac.entity.BizLogEntity;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author wggy
 * @create 2019-05-16 18:13
 **/
public interface BizLogService {

    @CachePut
    void saveLog(BizLogEntity log);

    @Cacheable(value="syslogs", key="#id")
    BizLogEntity getById(Long id);
}
