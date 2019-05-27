package com.wggy.sf.api.controller;

import com.wggy.sf.api.common.R;
import com.wggy.sf.api.model.ApiModel;
import com.wggy.sf.api.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/***
 *  create by wggy on 2019/5/27 20:40 
 **/
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping("insert/{configKey}/{configValue}")
    public R insert(@PathVariable("configKey") String configKey, @PathVariable("configValue") String configValue) {
        if (StringUtils.isBlank(configKey) || StringUtils.isBlank(configValue)) {
            return R.error("参数不能为空");
        }
        ApiModel apiModel = new ApiModel();
        apiModel.setConfigKey(configKey);
        apiModel.setConfigValue(configValue);
        apiService.save(apiModel);
        return R.ok();
    }

    @RequestMapping("query/id/{id}")
    public R queryById(@PathVariable("id") Long id) {
        if (id == null) {
            return R.error("参数不能为空");
        }
        return R.ok().put("data", apiService.findById(id));
    }

    @RequestMapping("query/key/{configKey}")
    public R queryByKey(@PathVariable("configKey") String configKey) {
        if (StringUtils.isBlank(configKey)) {
            return R.error("参数不能为空");
        }
        return R.ok().put("data", apiService.findByKey(configKey));
    }

    @RequestMapping("update/{id}/{configKey}/{configValue}")
    public R update(@PathVariable("id") Long id, @PathVariable("configKey") String configKey, @PathVariable("configValue") String configValue) {
        if (id == null || StringUtils.isBlank(configKey) || StringUtils.isBlank(configValue)) {
            return R.error("参数不能为空");
        }
        ApiModel apiModel = new ApiModel();
        apiModel.setId(id);
        apiModel.setConfigKey(configKey);
        apiModel.setConfigValue(configValue);
        apiService.update(apiModel);
        return R.ok();
    }

    @RequestMapping("delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        if (id == null) {
            return R.error("参数不能为空");
        }
        apiService.deleteById(id);
        return R.ok();
    }

    @RequestMapping(value = "/insert_q", method = RequestMethod.POST)
    public R insertQ(String configKey, String configValue) {
        if (StringUtils.isBlank(configKey) || StringUtils.isBlank(configValue)) {
            return R.error("参数不能为空");
        }
        ApiModel apiModel = new ApiModel();
        apiModel.setConfigKey(configKey);
        apiModel.setConfigValue(configValue);
        apiService.save(apiModel);
        return R.ok();
    }

}
