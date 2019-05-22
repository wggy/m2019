package com.wggy.prune.rbac.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author wggy
 * @create 2019-05-16 18:03
 **/
@Data
@Entity
@Table(name = "sys_biz_log")
public class BizLogEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    @Column(nullable = false)
    private String username;
    /**
     * 用户操作
     */
    @Column(nullable = false)
    private String operation;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;
    private String ip;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
