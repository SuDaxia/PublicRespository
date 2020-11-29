package com.syy.config.ext;

import com.syy.bean.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(this.getClass()+"...postProcessBeanFactory bean个数："+beanFactory.getBeanDefinitionCount());
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println(this.getClass()+"...postProcessBeanDefinitionRegistry bean个数："+registry.getBeanDefinitionCount());
        registry.registerBeanDefinition("hello", new RootBeanDefinition(Person.class));
    }
}
