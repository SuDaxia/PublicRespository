package com.syy.test;

import com.syy.config.MainConfigProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOCProfileTest {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigProfile.class);


    //已经在idea的run configuration中设置VM option 设置 -Dspring.profiles.active=prod  【主语profiles 有s结尾】至于 -ea是配置断言生效的，如果没有-ea，虽然不报错，但本来不该通过的用例，就会通过了
    @Test
    public void profileTest(){
        PrintIOCBeans.printBeans(annotationConfigApplicationContext);
    }
}
