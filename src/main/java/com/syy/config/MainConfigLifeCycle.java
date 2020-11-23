package com.syy.config;

import com.syy.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 这个探究容器中创建bean的生命周期 bean 创建 初始化 销毁
 * 容器管理bean的生命周期
 *  1）通过@Bean指定初始化 销毁方法
 *  默认单实例饿汉模式情况下，@Bean先创建、初始化完都在容器创建好之前，销毁在容器关闭之前
 *     单实例懒汉模式情况下@Lazy @Bean则创建何初始化都是在调用的时候才创建、初始化，均在容器创建好之后，销毁在容器关闭之前
 *      多实例模式，基本默认懒汉模式，使用的时候才去调用创建、初始化，也是在榕城区创建好之后，销毁在容器关闭之前
 *
 *  2）通过实现接口InitializingBean, DisposableBean 一个初始化一个销毁，然后该类使用@Bean注解，与上面的情形一致 默认在容器创建好之前就创建 初始化好，
 *     但是多实例@Scope("proto")、懒汉模式@Lazy 就在容器创建好之后使用的时候才会区创建、初始化
 *
 *  3）这是JSR250规范
 *   @PostConstruct在bean从容器中移除销毁之前调用
 *   @PreDestory bean销毁前回调通知我们进行清理工作
 *
 *  4） BeanPostProcessor接口的两个方法在初始化前后调用
 */

@Configuration
@ComponentScan({"com.syy.bean","com.syy.processor"})
public class MainConfigLifeCycle {
    /**
    @Bean(initMethod="myinit",destroyMethod="mydestroy")
    @Lazy
    //@Scope("prototype")
    public Car car() {
        return new Car();
    }
    */

    @Bean(initMethod="myinit",destroyMethod="mydestroy")//混合最多的一种方式
    //@Lazy
    //@Scope("prototype")
    public Cat cat(){
        return new Cat();
    }

    //@Bean()
    //public
}
