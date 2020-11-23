package com.syy.config;

import com.syy.bean.*;
import com.syy.factory.ColorFactoryBean;
import com.syy.profile.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

/**
 * 给容器中注册组件：
 * 1)、包扫描+组建标注注解 @Controller @Service @Repository @Component
 * 2)、@Bean 导入第三方包里面的组建
 * 3)、@Import 快速给容器中导入一个组件
 *      1)、@Import(要导入到容器中的组件)，容器中就会自动注册这个组件，id默认是全类名
 *      2）、ImportSelector:返回需要导入的组件的全类名数组
 *      3） ImportBeanDefinitionRegistrar:手动注册bean到容器中
 * 4）使用 Spring提供的FactoryBean来注册组件与bean
 *      1) 默认获取道德是工厂bean调用getObject创建的对象
 *      2） 要获取工厂bean本身，我们需要给id前面加上一个&
 */
//@Conditional({ClassCondition.class})

//@Configuration
//但如果引入的第三方包class，
@Import({Book.class, Car.class, Red.class, Yellow.class, MyImportSelect.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig3 {

    @Bean("Unknow")
    public Person person(){
        return new Person("xxx",0);
    }

    /**
     * @Conditional  @Target({ElementType.TYPE, ElementType.METHOD})可以标注在类上，也可以标注在方法上，两个都有的话优先级是类上面匹配了，再来方法里匹配，类都没匹配上方法不回去
     * 如何是window 返回Bill的对象，时linux则返回,否则反馈unknow对象，去写一个@Conditional里面需要的实现接口类
     * @return
     */

    @Conditional({WindowCondition.class})
    @Bean("Bill")
    public Person person1(){
        return new Person("Bill",67);
    }

    @Conditional({LinuxCondition.class, ClassCondition.class})
    @Bean("Linus")
    public Person person2(){
        return new Person("Linus",87);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
