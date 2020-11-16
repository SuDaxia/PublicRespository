package com.syy;

import com.syy.bean.Person;
import com.syy.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;

public class MainTest {

    public static void beanXmlConfig(){
        //默认就是src/main/resurces作为资源根目录"beans.xml" 可以,"classpath:beans.xml","classpath:/beans.xml","classpath:**/beans.xml" 都可以 **代表当前目录下的任意子目录
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:**/beans.xml");
        Person bean1 = (Person)applicationContext.getBean(Person.class);
        Person bean2 = (Person)applicationContext.getBean("person");
        System.out.println(bean1);
        System.out.println(bean1==bean2);

    }

    public static void annotationBeanConfig(){
        //以前是xml解析器context传入xml配置文件，现在是注解配置的context，传入配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Map<String,Person> personMap = annotationConfigApplicationContext.getBeansOfType(Person.class); //getBeanNamesForType(Person.class);
        System.out.println(Arrays.toString(personMap.keySet().toArray(new String[0])));

    }

    public static  void main(String[] args){
        //beanXmlConfig();
        annotationBeanConfig();
    }
}
