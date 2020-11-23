package com.syy.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Bean生命周期 ，默认单例 无@Lazy 无@Scope("prototype")则创建和初始化都在容器创建前，否则在容器创建后
 * 对对象来说：默认构造器constructor最早 -- 接口BeanPostProcessor.postProcessBeforeInitialization()方法 -->注解@PostConstruct --> 接口InitializingBean的afterPropertiesSet()方法 --> 注解@Bean(initMethod="myinit")-->BeanPostProcessor.postProcessAfterInitialization
 * -->|前面都算是创建、初始化前通知、初始化| 多实例和懒汉模式 再容器创建完后才开始创建、初始化前通知、初始化--->
 * -->@PreDestroy注解 -->destroy() --->@Bean(destroyMethod="mydestroy")   -->
 */

/**
 * 最终整理执行顺序(默认单例非懒加载，其实多实例就是跟懒加载一样使用才去构造创建bean)
 * 1、bean constructor ...本身构造器
 * 2、接口BeanPostProcessor.postProcessBeforeInitialization方法
 * 3、bean 方法注解@PostConstruct
 * 4、bean 接口InitializingBean实现
 * 5、bean 注解指定初始化方法@Bean(initMethod="myinit")
 * 6、接口BeanPostProcessor.postProcessAfterInitialization方法
 * 7、bean被获取使用中，eg调用其它普通方法
 * 8、bean 中注解制定销毁前调用@PreDestroy
 * 9、bean自身的destroy()方法
 * 10、bean 注解制定销毁方法@Bean(destroyMethod="mydestroy")
 */

/**
 * InitDestroyAnnotationBeanPostProcessor 就是利用BeanPostProcessor来实现 @PostCustruct 和@PreDestroy注解方法功能的
 */

/**
 * Spring的注解、自动赋值等等很多都是基于BeanPostProcessor来实现的
 */
//@Component
//public class Cat implements BeanPostProcessor,InitializingBean, DisposableBean {
//这里如果实现了BeanPostProcessor的两个方法也没有用

public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("cat constructor ...本身构造器");
    }
    @PostConstruct
    public void postConstruct(){
        System.out.println("cat postConstruct ...@PostConstruct");
    }

    //BeanPostProcessor接口的方法 初始化之前 【此处无用，实现接口方法返回让MyBeanPostProcessor无法对此Cat类调用此方法】
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //可以包装處理對bean，而後再init初始化
        System.out.println("cat postProcessBeforeInitialization 接口BeanPostProcessor before "+bean.getClass());
        //此处不处理直接返回
        return bean;
    }

    //InitializingBean接口
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat init ...InitializingBean接口");
    }

    //@Bean(initMethod="myinit")
    public void myinit(){
        System.out.println("cat init with no paramters  @Bean(initMethod=\"myinit\")");
    }

    //BeanPostProcessor接口的方法 初始化之后回调再包装 【此处无用，实现接口方法返回让MyBeanPostProcessor无法对此Cat类调用此方法】
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("cat postProcessAfterInitialization 接口BeanPostProcessor  after "+bean.getClass());
        //此处不处理直接返回
        return bean;
    }

    //@PreDestroy注解
    @PreDestroy
    public void destory(){
        System.out.println("cat PreDestroy ...@PreDestroy注解");
    }

    public void destroy() throws Exception {
        System.out.println("cat destory ...destroy()");
    }

    //@Bean(destroyMethod="mydestroy")
    public void mydestroy(){
        System.out.println("cat destory with no paramters  @Bean(destroyMethod=\"mydestroy\")");
    }

    public void use(){
        System.out.println("cat  被使用了拉");
    }

}
