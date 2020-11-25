package com.syy.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


/**
 * 声明式事务：
 *
 * 环境搭建：
 * 1。导入相关依赖
 *      数据源、数据库驱动、Spring-jdbc模块
 * 2。配置数据源、JdbcTemplate (Spring提供的简化数据库操作的工具)操作数据
 * 3。事务方法上 @Transactional 表示当前方法是一个事务
 * 4。@EnableTransactionManagement 开启基于注解的事物方法
 * 5。配置事务管理器来控制事务
 *
 * 测试事务回滚，会发现回滚后，的自增id是回不去的，当再正常插入下一条，就发现自增id不连续了
 */

/**
 * 原理：@EnableTransactionManagement
 * 1。@EnableTransactionManagement
 *      @Import(TransactionManagementConfigurationSelector.class)
 *      导入两个组组件
 *      AutoProxyRegistrar
 *      ProxyTransactionManagementConfiguration
 *  2。AutoProxyRegistrar，
 *      给容器中注册一个InfrastructureAdvisorAutoProxyCreator
 *      利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象，代理对象执行方法利用拦截器进行事务控制
 *
 *
 *
 *
 *
 *  3。ProxyTransactionManagementConfiguration 做了什么
 *      1。给容器中注册事务增强器
 *          1。事务增强器要用事务注解信息 AnnotationTransactionAttributeSource解析事务
 *          2。事务拦截器：
 *              TransactonInterceptor：保存了事务属性信息，事务管理器
 *              踏实一个MethoInterceptor
 *              在目标方法执行的时候：
 *                  执行拦截器链：
 *                  失误拦截器：
 *                      1。先获取事务相关属性
 *                      2。再获取PlatformTranactionManager,如果实现没有执行任何transactionManager,最终会从容器中获取一个
 *                      3。执行目标方法
 *                          如果异常，获取到事务管理器，利用事务管理进行回滚
 *                          如果正常，利用事务管理器，提交事务
 *
 *
 *
 */

@EnableTransactionManagement
@Configuration
public class MainConfigTX {
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("12346");
        dataSource.setDriverClass("com.myslq.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dev");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        //Spirng 对Configuration类特殊处理，给容器中加组件，多次调用都只从容器中获取组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    //注册事务管理器到容器中
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
