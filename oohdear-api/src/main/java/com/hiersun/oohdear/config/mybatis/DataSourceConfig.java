package com.hiersun.oohdear.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by liubaocheng on 2016/11/14.
 */
@Configuration
public class DataSourceConfig {
    private Logger logger = LoggerFactory
            .getLogger(DataSourceConfig.class);


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }





}
