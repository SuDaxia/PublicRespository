package com.syy.test;

import com.syy.aop.MathCalculator;
import com.syy.config.MainConfigAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCAOPTest {
    AnnotationConfigApplicationContext annotationConfigApplicationContext= new AnnotationConfigApplicationContext(MainConfigAOP.class);

    @Test
    public void aopTest(){

        /**
         *1. 行不通，需要通过IOC容器拿的，才能去代理执行切面过程
         *
         *         MathCalculator mc = new MathCalculator();
         *         System.out.println(mc.div(8,4));
         */
        MathCalculator mc = annotationConfigApplicationContext.getBean(MathCalculator.class);
        System.out.println(mc.div(8,4));

    }
}
