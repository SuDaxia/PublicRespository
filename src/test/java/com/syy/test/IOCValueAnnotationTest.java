package com.syy.test;

import com.syy.bean.Person;
import com.syy.config.MainConfigValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCValueAnnotationTest {
    AnnotationConfigApplicationContext annotationConfigApplicationContext= new AnnotationConfigApplicationContext(MainConfigValue.class);

    @Test
    public void valueAnnotationTest(){
        printBeans(annotationConfigApplicationContext);
        Person p = (Person)annotationConfigApplicationContext.getBean(Person.class);
        System.out.println(p.toString());
        //还可以通过环境信息获取properties的变量值
        System.out.println(annotationConfigApplicationContext.getEnvironment().getProperty("project.name"));
    }

    private void printBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext){
        String[]  definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for(String name: definitionNames){
            System.out.println(name);
        }
        System.out.println("------>");
    }
}
