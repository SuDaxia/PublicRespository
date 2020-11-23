package com.syy.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class RedAware implements ApplicationContextAware, EmbeddedValueResolverAware {

    //可以下断点，查看同类型的一些是怎么调用的原理
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获得IOC容器，然后做一些羞羞的事情
        System.out.println(applicationContext.getEnvironment().getProperty("os.name"));
    }

    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        //获得资源解析器，可以解析一些东西
        System.out.println(resolver.resolveStringValue("${project.name},#{1+24.5},我是好人"));
    }
}
