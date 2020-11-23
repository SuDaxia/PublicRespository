package com.syy.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @Profile Spring的注解，方便的动态切换环境
 * 默认值是 default，相应的是Sprint.profile.active参数，在程序运行时给定该参数即可 -Dsprint.profiles.active=prod 【注意profiles的s结尾，参数设置在VM options中，不是传递给main(String[] args)】
 *
 */

@PropertySource("classpath:config/application.properties")
@Configuration
public class MainConfigProfile {

    @Value("${db.user}")
    private String user;

    @Value("${db.passwd}")
    private String passwd;

    @Value("${db.jdbcurl}")
    private String jdbcurl;

    @Value("${db.driver}")
    private String driver;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        //TODO 说不定还得加解密
        this.passwd = passwd;
    }

    public String getJdbcurl() {
        return jdbcurl;
    }

    public void setJdbcurl(String jdbcurl) {
        this.jdbcurl = jdbcurl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Profile("dev")
    //@Profile("default")
    @Bean("devDataSource")
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(passwd);
        dataSource.setJdbcUrl(jdbcurl);
        dataSource.setDriverClass(driver);
        return dataSource;
    }

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Drive");
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/prod");
        dataSource.setDriverClass("com.mysql.jdbc.Drive");
        return dataSource;
    }
}
