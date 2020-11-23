package com.syy.test;

import com.syy.bean.Cat;
import com.syy.config.MainConfigLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCBeanLifeCycleTest {

    @Test
    public void cycleTest2(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
        System.out.println("容器创建好..");
        //annotationConfigApplicationContext.getBean("cat");//注意时Cat类


        Object obj = annotationConfigApplicationContext.getBean("cat");//注意时Cat类
        if (obj!=null){
            System.out.println("getBean的类型:"+obj.getClass());
            ((Cat)obj).use();
        }else{
            System.out.println("getBean的对象null:");
        }

        annotationConfigApplicationContext.close();
        System.out.println("容器关闭..");
    }

    @Test
    public void cycleTest1(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
        System.out.println("容器创建好..");
        annotationConfigApplicationContext.getBean("car");
        annotationConfigApplicationContext.close();
        System.out.println("容器关闭..");
    }
}
