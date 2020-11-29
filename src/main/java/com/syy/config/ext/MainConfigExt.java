package com.syy.config.ext;

import com.syy.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring  扩展(BeanPostProcessor,BeanDefinitionRegistryPostProcessor,ApplicationListener)原理：
 *  BeanPostProcessor : bean 后置处理器，bean创建对象初始化前后进行拦截工作的
 *  BeanFactoryPostProcessor : beanFactory的后置处理器：
 *      在BeanFactory标准初始化之后调用：所有的bean定义已经保存加载到beanFactory，但bean的实例还未创建
 *      1.IOC容器创建
 *      2。invokeBeanFactoryPostProcessors(beanFactory);
 *          如何找到所有的BeanFactoryPostProcessor
 *              1。直接在BeanFactory中找到所有类型是BeanPostProcessor的对象
 *              2。再初始化创建其它组建前面执行
 * BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
 *      在bean定义信息将要被加载，bean实例还未被创建
 *
 *      优先于 BeanFactoryPostProcessor，可以自己给容器中再额外加些组件
 *
 * BeanDefinitionRegistryPostProcessor原理：invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
 *      1。IOC创建对象
 *      2。refresh()->invokeBeanFactoryPostProcessors(beanFactory)->invokeBeanDefinitionRegistryPostProcessors
 *      3.从容器中获取到所有BeanDefinitionRegistryPostProcessor组件，依次触发postProcessBeanDefinitionRegistry
 *          // Now, invoke the postProcessBeanFactory callback of all processors handled so far.
 * 			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
 * 			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
 *            1。依次触发所有的postProcessBeanDefinitionRegistry()方法
 *            2。再来依次触发postProcessBeanFactory
 *      4。再来从容器中找到BeanFactoryPostProcessor组建，然后依次出发 再来依次触发postProcessBeanFactory()方法
 *
 * ApplicationListener : 监听容器中发布的事件，事件驱动模型开发：
 *  public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *      监听ApplicationEvent 及其下面的子事件：
 *
 *  自定义接受发布时间步骤：
 *      1。方法一：写一个监听器来监听某个事件(ApplicationEvent及其子类)
 *         方法二：注解方式 @EventListener(classes={ApplicationEvent.class})在方法上
 *      2。把监听器加入到容器
 *      3。只要容器中有相关事件的发布，我们就能监听到这个事件：
 *          ContextRefreshedEvent 容器刷新完成(所有bean都完全创建) 会发布这个事件
 *          ContextClosedEvent 关闭容器会发布这个事件
 *      4。发布一个事件：
 *           annotationConfigApplicationContext.publishEvent(new ApplicationEvent("自定义发布事件")
 *
 * 原理：
 *      ContextRefreshedEvent IOCExt$1[source=自定义发布事件] ContextClosedEvent
 *      1。ContextRefreshedEvent：
 *          1。容器创建对象：refresh()
 *          2。finishRefresh();容器刷新完成
 *          3。publishEvent(new ContextRefreshedEvent(this));
 *                  事件发布流程：
 *                  1。获取事件多播器（派发器）getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
 *                  2。multicastEvent派发事件
 *                  3。获取到所有的ApplicationListener:
 *                      for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *                      1。如果有Executor 可以支持使用Executor进行异步派发：
 *                          Executor executor = getTaskExecutor()
 *                      2。否则，同步的方式直接执行listener方法，invokeListener(listener, event)
 *                      拿到listener回调onApplicationEvent方法
 *
 *  【事件多播器（派发器）】原理
 *      1。容器创建对象：
 *      refresh()
 *          initApplicationEventMulticaster();初始化
 *              先去容器中获取看有没有 APPLICATION_EVENT_MULTICASTER_BEAN_NAME=“applicationEventMulticaster”的多播器
 *              如果没有就创建一个简单的注入到容器中
 *                  this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 * 			        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
 *
 *  【容器中有哪些监听器】
 *      1。容器创建对象：
 *  *      refresh()
 *      2。registerListeners()
 *          从容其中拿到所有监听器把他们注册到 applicationEventMulticaster 中
 *          String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *          //将监听器注册到 applicationEventMulticaster 中
 *          getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 * @EventListener 中 可以看到上面提示 @see EventListenerMethodProcessor 这个来处理注解的
 * 原理：
 *  public class EventListenerMethodProcessor implements SmartInitializingSingleton, ApplicationContextAware
 *      SmartInitializingSingleton 的 afterSingletonsInstantiated原理：
 *          下断点在 EventListenerMethodProcessor.afterSingletonsInstantiated上
 *              refresh();
 *                  finishBeanFactoryInitialization(beanFactory);
 *                      beanFactory.preInstantiateSingletons();
 *                          获取所有单实例bean，然后看是否实现了SmartInitializingSingleton接口
 *                          如果是 smartSingleton.afterSingletonsInstantiated();
 *
 *
 */
@Configuration
@ComponentScan({"com.syy.config.ext"})
public class MainConfigExt {

    @Bean
    public Cat cat(){
        return new Cat();
    }

}
