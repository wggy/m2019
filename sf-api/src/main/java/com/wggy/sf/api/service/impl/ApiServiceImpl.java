package com.wggy.sf.api.service.impl;

import com.wggy.sf.api.model.ApiModel;
import com.wggy.sf.api.repo.ApiRepo;
import com.wggy.sf.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/***
 *  create by wggy on 2019/5/27 20:43 
 **/
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiRepo apiRepo;

    @Override
    public void save(ApiModel model) {
        apiRepo.save(model);
    }

    @Override
    @Transactional
    public void update(ApiModel model) {
        apiRepo.updateKeyValue(model.getConfigKey(), model.getConfigValue(), model.getId());
    }

    @Override
    public ApiModel findByKey(String configKey) {
        return apiRepo.findByKey(configKey);
    }

    @Override
    public ApiModel findById(Long id) {
        return apiRepo.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        apiRepo.deleteById(id);
    }

    @Override
    public void deleteByKey(String key) {
        apiRepo.deleteByConfigKey(key);
    }
}
