package com.syy.bean;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope("prototype") //@Scope("prototype")会让@PreDestory 和DisposableBean接口功能不会被调用？好奇怪，容器关闭前后都不会出现
@Lazy
public class Dog implements InitializingBean, DisposableBean {
    public void destroy() throws Exception {
        System.out.println("dog destroy ...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("dog afterPropertiesSet init ...");
    }

    public Dog(){
        System.out.println("dog constructor ...");
    }


    public void init(){
        System.out.println("dog init ...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("dog PreDestroy ...");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("dog postConstruct ...");
    }
}
