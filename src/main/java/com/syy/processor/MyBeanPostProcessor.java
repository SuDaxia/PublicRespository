package com.syy.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**BeanPostProcessor原理 ，下断点追踪，可以按照堆栈调用，可以发现如下：
 *
 * populateBean(beanName, mbd, instanceWrapper); 给整个属性赋值，后面才
 *initializeBean(beanName, exposedObject, mbd){
 *  一：applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);去执行BeanPostBeforeInitalization方法
 *          其中先遍历容器中的所有 BeanPostProcessor，依次执行去执行BeanPostBeforeInitalization方法，
 *          但是一旦某执行结果null就不再执行剩下的BeanPostBeforeInitalization方法了，直接返回
 *  二：然后applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); 去进行bean的初始化init
 *  三：再 applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);去执行BeanPostAfterInitalizaiton方法
 * }
 *
 *
 */

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    //@Override
    //可以此处下断点，在bean初始化前的调用，前面做了那些事
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(" BeanPostProcessor.postProcessBeforeInitialization  ..."+bean.getClass());
        return bean;
    }

    //@Override
    //可以此处下断点，在bean初始化后的调用，前面做了那些事
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(" BeanPostProcessor.postProcessAfterInitialization  ..."+bean.getClass());
        return bean;
    }
}
