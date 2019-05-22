package com.wggy.prune.rbac.repo;

import com.wggy.prune.rbac.entity.BizLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wggy
 * @create 2019-05-16 18:11
 **/
@Repository
public interface BizLogRepo extends JpaRepository<BizLogEntity, Long> {

//    @Query(value = "SELECT * FROM sys_biz_log WHERE id = ?1 limit 1", nativeQuery = true)
//    BizLogEntity findById(String id);
}
