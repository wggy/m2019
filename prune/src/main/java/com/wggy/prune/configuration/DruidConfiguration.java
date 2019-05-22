package com.wggy.prune.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ping
 * @create 2019-05-16 14:42
 **/
@Slf4j
@Configuration
public class DruidConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource druidDataSource(){
        log.info(">>>>>>>>>>>>>>>>>>加载Druid连接池<<<<<<<<<<<<<<<<<<<<<<");
        return DruidDataSourceBuilder.create().build();
    }
}
