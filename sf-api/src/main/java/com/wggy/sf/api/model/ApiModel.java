package com.wggy.sf.api.model;

import lombok.Data;

import javax.persistence.*;

/***
 *  create by wggy on 2019/5/27 21:09 
 **/
@Data
@Entity
@Table(name = "tbl_config")
public class ApiModel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String configKey;

    private String configValue;
}
