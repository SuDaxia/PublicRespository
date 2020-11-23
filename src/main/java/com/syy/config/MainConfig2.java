package com.syy.config;

import com.syy.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig2 {
    @Bean
    /**
     * Specifies the name of the scope to use for the annotated component/bean.
     * <p>Defaults to an empty string ({@code ""}) which implies
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON SCOPE_SINGLETON}.
     * @since 4.2
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype 多实例
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON singleton 单实例
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     * @see #value
     */
    //singleton 单实例创建好context对象时，就已经创建好单实例了
    //prototype 多实例时，再容器创建好是没有创建，而是是在获取bean的时候才去创建bean
    // 懒加载@Lazy 在单实例时，也是只创建一次，在初次获取时才去创建
    @Scope("prototype")//设置多实例，容器中每次拿就是多个新的对象实例了
    public Person person(){//默认用方法名作为实例名name
        System.out.println("给容器中创建实例aaa...");
        return new Person("bill",22);
    }

    @Bean
    @Lazy
    public Person person2(){
        System.out.println("给容器中创建实例bbb...");
        return new Person("bill",44);
    }
}
