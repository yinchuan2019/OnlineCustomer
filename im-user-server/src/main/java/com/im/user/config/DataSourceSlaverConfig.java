package com.im.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 映射配置：@MapperScan - datasource - *Mapper.xml
 */
//注册到springboot容器，相当于原来xml文件里的<beans>
@Configuration
//下面要进行扫包，目的是标清楚为谁添加的数据源，这样对应的包里函数执行数据库操作的时候，就知道要执行的数据库账号,密码，表，以及事务处理之类的。
@MapperScan(basePackages = {"com.app.user.dao.slaverDao"}, sqlSessionTemplateRef = "slaverSqlSessionTemplate")
public class DataSourceSlaverConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slaver")
    public DruidDataSource slaverDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public SqlSessionFactory slaverSqlSessionFactory(@Qualifier("slaverDataSource") DruidDataSource dataSource) {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        SqlSessionFactory factory = null;
        try {
            //配置*Mapper.xml
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*Mapper.xml"));
            factory = bean.getObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return factory;
    }

    @Bean
    public DataSourceTransactionManager slaverTransactionManager(@Qualifier("slaverDataSource") DruidDataSource dataSource) {
        System.out.println(dataSource);
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate slaverSqlSessionTemplate(@Qualifier("slaverSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
