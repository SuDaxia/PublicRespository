package com.syy.test;

import com.syy.bean.Person;
import com.syy.config.MainConfig;
import com.syy.config.MainConfig2;
import com.syy.config.MainConfig3;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig3.class);
    @Test
    public void testFactoryBean(){
        printBeans(annotationConfigApplicationContext);
        //这里就不要强行转换(ColorFactoryBean)了
        Object obj = annotationConfigApplicationContext.getBean("colorFactoryBean");
        Object ob2 = annotationConfigApplicationContext.getBean("colorFactoryBean");
        System.out.println("ColorFactoryBean 实际获取的bean类型:"+obj.getClass());
        //与FactoryBean中设置是否单例有关
        System.out.println(obj==ob2);
        Object obj3 = annotationConfigApplicationContext.getBean("&colorFactoryBean");
        System.out.println("获取真正工厂Bean的ColorFactoryBean 需要&前缀符号:"+obj3.getClass());
    }
    private void printBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext){
        String[]  definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for(String name: definitionNames){
            System.out.println(name);
        }
        System.out.println("------>");
    }
    @Test
    public void test04(){
        printBeans(annotationConfigApplicationContext);
        System.out.println("------>");


    }

    @Test
    public void test03(){
        String[]  definitionNames = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
        for(String name: definitionNames){
            System.out.println(name);
        }
        System.out.println("------>");

    }

    @Test
    public void test02(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[]  definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for(String name: definitionNames){
            System.out.println(name);
        }
        Person p1 = (Person)annotationConfigApplicationContext.getBean("person");
        Person p2 = (Person)annotationConfigApplicationContext.getBean("person");
        System.out.println(p1==p2);
        Person p3 = (Person)annotationConfigApplicationContext.getBean("person2");
        Person p4 = (Person)annotationConfigApplicationContext.getBean("person2");
        System.out.println(p3==p4);

    }

    @Test
    public void test01(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
         String[]  definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
         for(String name: definitionNames){
             System.out.println(name);
         }
    }
}
