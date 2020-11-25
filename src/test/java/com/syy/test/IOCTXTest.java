package com.syy.test;

import com.syy.config.MainConfigTX;
import com.syy.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 声明式事务 注解 测试
 */
@ComponentScan("com.syy")
public class IOCTXTest {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigTX.class);

    @Test
    public void txTest(){
        UserService userService = annotationConfigApplicationContext.getBean(UserService.class);
        userService.insertUser();
    }
}
