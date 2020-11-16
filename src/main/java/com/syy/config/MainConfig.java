package com.syy.config;

import com.syy.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置注解，替换以前的bean.xml config.xml之类的
@Configuration
public class MainConfig {

    //@Bean注解声明一个对象实例放进IOC容器中,不设置默认就是方法名作为注入bean的名字
    @Bean
    public Person person(){
        return new Person("aa",22);
    }

    @Bean
    public Person person2(){
        return new Person("222",65);
    }
    @Bean("Bill")
    public Person person3(){
        return new Person("Bill",78);
    }

    @Bean(name="Bill")
    public Person person4(){
        return new Person("ttt",78);
    }
}
