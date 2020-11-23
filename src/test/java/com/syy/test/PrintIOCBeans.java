package com.syy.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PrintIOCBeans {

    public static void printBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext){
        if (annotationConfigApplicationContext!=null){
            for(String  beanName : annotationConfigApplicationContext.getBeanDefinitionNames()){
                System.out.println(beanName);
            }
        }
        System.out.println("------>");
    }
}
