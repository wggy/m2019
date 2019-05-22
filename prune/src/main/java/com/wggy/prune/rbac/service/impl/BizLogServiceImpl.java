package com.wggy.prune.rbac.service.impl;

import com.wggy.prune.rbac.entity.BizLogEntity;
import com.wggy.prune.rbac.repo.BizLogRepo;
import com.wggy.prune.rbac.service.BizLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author wggy
 * @create 2019-05-16 18:13
 **/
@Slf4j
@Service
public class BizLogServiceImpl implements BizLogService {

    @Autowired
    private BizLogRepo bizLogRepo;
    @Override
    public void saveLog(BizLogEntity log) {
        bizLogRepo.save(log);
    }

    @Override
    public BizLogEntity getById(Long id) {
        log.info("访问数据库.....................");
        Optional<BizLogEntity> optional = bizLogRepo.findById(id);
        return optional.orElse(null);
    }
}
