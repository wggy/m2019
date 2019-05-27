package com.wggy.sf.api.service;

import com.wggy.sf.api.model.ApiModel;

/***
 *  create by wggy on 2019/5/27 20:41 
 **/
public interface ApiService {
    void save(ApiModel model);

    void update(ApiModel model);

    ApiModel findByKey(String configKey);

    ApiModel findById(Long id);

    void deleteById(Long id);

    void deleteByKey(String key);
}
