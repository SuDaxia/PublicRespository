package com.syy.config;

import com.syy.controller.BookController;
import com.syy.dao.BookMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * (1)自动装配 注解 @Autowired，须制定扫描的包，将对应的controller service dao都扫描注入进去
 * @Autowired默认按类型找，当找到多个，按类名首字母小写找，找不到就报错了，
 * 如果有多个实例，要指定那个用@Qualifier指定安id查找，而不是属性名
 * 当找不到的时候，为了不报错，可以@Autowired(required=false)来表示非必须的，会所有初始化一个出来
 *
 * @Primary则是指定默认情况下首选装配什么，与属性名无关，但是@Qualifier的优先级更高，会覆盖@Primary
 * 即两者都有的情况下，@Qualifier最终生效
 *@Autowired 在构造、属性、方法、参数上都可以使用
 *
 * 主要是 xxxxAware的都是继承 Aware 接口，实现回调的时候自动装配获取bean
 */

/**
 * （2） @Autowired是Spring的自动装配，还有@Resource(JSR250规范) @Inject(JSR330规范)时java的注解，
 * 同样的效果，
 *  @Resource 不支持@Primary 以及@Autowired(required=fales)功能
 *
 * @Inject需要导入javax.inject 和@Autowired一样但没有 required=false功能
 *
 */
/**
 * 自动装配靠的容器中的AutowiredAnnotationBeanPostProcessor来实现的
 */
@Configuration
@PropertySource("classpath:config/application.properties")
@ComponentScan({"com.syy.dao","com.syy.service","com.syy.controller","com.syy.bean"})
public class MainConfigAutowired {

    //@Bean
    public BookController bookController(){
        return new BookController();
    }

    //@Primary
    @Bean("bookMapper2")
    public BookMapper bookMapper(){
        BookMapper b = new BookMapper();
        b.setLabel("222");
        return b;
    }

}
