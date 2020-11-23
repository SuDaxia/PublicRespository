package com.syy.config;

import com.syy.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource({"classpath:/config/application.properties"})//可以对应beans.xml中的<context property-placeholder> 让${} #{}去配置文件终点值进行覆盖
public class MainConfigValue {

    @Bean
    public Person person(){
        return new Person();
    }
}
