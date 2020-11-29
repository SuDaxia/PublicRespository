package com.syy.config.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(this.getClass()+" ... "+"postProcessBeanFactory ");
        int count =beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println(this.getClass()+"...当前BeanFacotry中有"+count+"个bean");
        System.out.println(this.getClass()+"...当前BeanFacotry中有"+ Arrays.toString(names));
    }
}
