package com.syy.config;

import com.syy.bean.Person;
import com.syy.filter.MyTypeFilter;
import org.springframework.context.annotation.*;


//配置注解，替换以前的bean.xml config.xml之类的
@Configuration
/*
@ComponentScan(value = "com.syy",excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Bean.class, Service.class})},includeFilters = {}
        )
*/
@ComponentScans(
        value={
@ComponentScan(value = "com.syy",includeFilters = {
        /*@ComponentScan.Filter(type= FilterType.ANNOTATION,classes={ Controller.class}),
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),*/
        @ComponentScan.Filter(type=FilterType.CUSTOM,classes = {MyTypeFilter.class})
        }
        ,useDefaultFilters = false
)})
//@ComponentScan value指定扫描的包
// excludeFilter = Filter[]排除哪些 @ComponentScan(value = "com.syy",excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Bean.class, Service.class})})
// includeFilters = Filter[]标明只需要包含，但是需要设置过滤useDefaultFilters=false，这个默认是true (同样xml配置里的也有对应的设置Filter)
//java8开始@ComponentScan可以重复使用@Repeatable(ComponentScans.class)，如果是之前的版本@ComponentScans(value={@ComponentScan(),@ComponentScan()})形式
//FilterType有5种方式 查看里面定义 的枚举方式，第五个FilterType.CUSTOM 必须是一个typeFilter接口的实现类，另写一个MyTypeFilter.java
public class MainConfig {

    //@Bean注解声明一个对象实例放进IOC容器中,不设置默认就是方法名作为注入bean的名字
    @Bean
    public Person person(){
        return new Person("aa",22);
    }

    @Bean
    public Person person2(){
        return new Person("222",65);
    }
    @Bean("Bill")
    public Person person3(){
        return new Person("Bill",78);
    }

    @Bean(name="Bill")
    public Person person4(){
        return new Person("ttt",78);
    }
}
