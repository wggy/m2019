package com.wggy.sf.api.repo;

import com.wggy.sf.api.model.ApiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/***
 *  create by wggy on 2019/5/27 20:43 
 **/
@Repository
public interface ApiRepo extends JpaRepository<ApiModel, Long> {

    @Query(value = "select * from tbl_config where config_key = ?1 limit 1", nativeQuery = true)
    ApiModel findByKey(String key);

    @Modifying
    @Query("delete from ApiModel u where u.configKey = ?1")
    void deleteByConfigKey(String key);

    @Modifying
    @Query(value = "delete from tbl_config where config_key = ?1", nativeQuery = true)
    void deleteByConfigKey2(String key);

    @Modifying
    @Query(value = "update tbl_config set config_key = ?1, config_value = ?2 where id = ?3", nativeQuery = true)
    void updateKeyValue(String key, String value, Long id);
}
