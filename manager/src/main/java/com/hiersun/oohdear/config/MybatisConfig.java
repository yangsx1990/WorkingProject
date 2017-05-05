package com.hiersun.oohdear.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;


@Configuration
@ConditionalOnClass({ EnableTransactionManagement.class })
@AutoConfigureAfter({ DataSourceConfig.class })
public class MybatisConfig {


    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;


    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;



    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.put("dialect","mysql");
        properties.put("offsetAsPageNum",true);
        properties.put("rowBoundsWithCount",true);
        properties.put("pageSizeZero",true);
        properties.put("reasonable",true);
        properties.put("supportMethodsArguments",false);
        properties.put("returnPageInfo","none");
        pageHelper.setProperties(properties);
        return pageHelper;
    }


    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource,PageHelper pageHelper) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // SET dataSource
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);

        Resource[] mapperLocation = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        sessionFactory.setMapperLocations(mapperLocation);
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});

        return sessionFactory;
    }

    // Spring 事务
    @Bean(name = "txManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }


    @Bean(name="mapperScannerConfigurer")
    public MapperScannerConfigurer MapperScannerConfigurer1() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.hiersun.oohdear.mapper");//"com.jr.mapper";
        Properties properties = new Properties();
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        properties.setProperty("mappers",Mapper.class.getName());
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

}
