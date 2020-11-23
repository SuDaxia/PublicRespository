package com.syy.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//实现接口，就能自己做IOC容器了
public class MyContextIOC implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    //这个接口方法中就会有容器传进来，自己就来实现容器里面加东西了
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
