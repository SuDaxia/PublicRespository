package com.syy.config;

import com.syy.aop.LogSpringAspect;
import com.syy.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring AOP的动态代理 (不同于java原生的 eclipse java aspects，spring aop 属于简化的功能相对弱些的切面，不需要额外的编译)
 *  1
 *  2 定义一个业务逻辑类
 *  3 定义一个日志切面类
 *  4 给切面类标准 何时何地切入运行（通知注释）
 *  5 将切面类与业务逻辑类（目标方法类）都加入到容器中
 *  6 必须要告诉容器那个是切面类
 *
 *
 */

@EnableAspectJAutoProxy //开启基于注解的aop模式
@Configuration
@ComponentScan("com.syy.aop")
public class MainConfigAOP {

    //业务逻辑类加入到容器中
    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogSpringAspect logSpringAspect(){
        return new LogSpringAspect();
    }

}
