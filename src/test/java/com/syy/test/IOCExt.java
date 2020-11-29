package com.syy.test;

import com.syy.config.ext.MainConfigExt;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring扩展注解 测试 BeanPostProcessor
 */
public class IOCExt {

    @Test
    public void ext(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigExt.class);
        annotationConfigApplicationContext.publishEvent(new ApplicationEvent("自定义发布事件") {
        });
        annotationConfigApplicationContext.close();
    }
}
