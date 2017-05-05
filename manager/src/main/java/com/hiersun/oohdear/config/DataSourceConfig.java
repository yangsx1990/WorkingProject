package com.hiersun.oohdear.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author saixing_yang@hiersun.com
 * @date ����ʱ�䣺2017��3��3�� ����6:06:46
 * @version 1.0
 */
@Configuration
public class DataSourceConfig {

	@Value("${druid.master.url}")
	private String url;
	@Value("${druid.master.username}")
	private String username;
	@Value("${druid.master.password}")
	private String password;
	@Value("${druid.master.driver_class_name}")
	private String driver_class_name;

	@Bean(destroyMethod = "close", initMethod = "init", name = "dataSource")
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driver_class_name);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		return druidDataSource;
	}
}
