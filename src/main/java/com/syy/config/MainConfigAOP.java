package com.syy.config;

import com.syy.aop.LogSpringAspect;
import com.syy.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring AOP的动态代理 (不同于java原生的 eclipse java aspects，spring aop 属于简化的功能相对弱些的切面，不需要额外的编译)
 *  1 必须开启aop自动代理 @EnableAspectJAutoProxy
 *  2 定义一个业务逻辑类
 *  3 定义一个日志切面类
 *  4 给切面类标注 @PointCut @After ... 何时何地切入运行（通知注释）
 *  5 将切面类与业务逻辑类（目标方法类）都加入到容器中 @Bean @Bean
 *  6 必须要告诉容器那个是切面类 @Aspect
 *  7 通过容器来获取，而不是自己手动new一个，再去执行方法
 */

/**
 * AOP原理
 * 先@EnableAspectJAutoProxy
 * 1. @EnableAspectJAutoProxy是什么，
 *          里面有 @Import( AspectJAutoProxyRegistrar.class )容器中导入这个组件
 *              利用ImportBeanDefinitionRegistrar接口实现，自定义注册了一个bean，下断点查看有什么bean
 *                  如果容器中有 org.springframework.aop.config.internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator 就检查是不是，
 *                          首次是没有的，会去注册一个
 *              然后拿到 enableAspectJAutoProxy注解信息，判定对应其它设置(proxyTargetClass exposeProxy)这个例子是没有的
 * 2. 研究核心 AnnotationAwareAspectJAutoProxyCreator 组件（后面其它的Enablexxxx类的注解，也是同样的类似看有没有加上什么组件，查看这个组件的功能，基本就了解了一点原理）
 *      AspectJAwareAdvisorAutoProxyCreator
 *          --> AspectJAwareAdvisorAutoProxyCreator
 *              --> AbstractAutoProxyCreator extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                  关注 SmartInstantiationAwareBeanPostProcessor BeanFactoryAware 后置处理器、自动装配Bean工厂
 *                  然后要下断点分析了，但是不一定最最外围的AspectJAwareAdvisorAutoProxyCreator能找到调用的重写方法，需要从实现抽象类开始往上查看实现后置处理器 bean工厂接口实现处下断点
 *                  AbstractAutoProxyCreator 直接implements接口开始，查看它实现的，有bean工厂要实现的 setBeanFactory接口
 * 断点一 AbstractAutoProxyCreator.setBeanFactory
 * 断点二 接口 AbstractAutoProxyCreator.postProcessBeforeInstantiation  注意不是postProcessBeforeInitialization
 * 断点三     AbstractAutoProxyCreator.postProcessBeforeInstantiation 注意不是postProcessAfterInitialization
 *
 *             往上层查看，AspectJAwareAdvisorAutoProxyCreator
 * 断点四五 AbstractAdvisorAutoProxyCreator.setBeanFactory 重写了，里面调了 initBeanFactory也要断点
 *
 *            往上AspectJAwareAdvisorAutoProxyCreator 没有什么重写 bean工厂、beanPostProcessor的不用管
 *
 *            再往上 AspectJAwareAdvisorAutoProxyCreator.initBeanFactory
 * 断点六 AnnotationAwareAspectJAutoProxyCreator.initBeanFactory 重写了 前面有用到的initBeanFactory方法
 *
 * 启动测试用例，开始断点追踪
 * 然后断点到：AbstractAdvisorAutoProxyCreator.setBeanFactory，怎么来的呢，查看堆栈信息，往后可以看到启动用力IOCAOPTest，然后逐层调用来的
 *     期间流程：
 *      1. 传入配置类，创建ioc容器
 *      2. 注册配置类，调用refresh()方法
 *          。。。
 *           registerBeanPostProcessors(beanFactory);注册后置处理器 来方便拦截 bean的创建
 *                  非调试，点进去看到实现源码 PostProcessorRegistrationDelegate.registerBeanPostProcessors()
 *                   1. 先获取ioc容器中已经定义了需要创建对象的所有BeanPostProcessor
 *                   2. 给ioc容器中添加 别的beanPostProcessor
 *                   3 优先注册实现了 PriorityOrdered.class接口排序的beanPostProcessor，再Ordered.class接口的，再其它的，分三类放在不同集合中
 *                   4. 会查询发现没有beanPostProcessor，就会去createBean创建这样的beanPostProcessor保存到容器中：
 *                      创建internalAutoProxyCreator
 *                      1。创建bean实力
 *                      2。populateBean 给bean的属性赋值
 *                      3。 initialization 初始化bean
 *                          1。invokeAwareMethods()判定是不是aware接口则进行相关处理
 *                                这中间有探测到时 BeanFactoryAware ，所以去调用了 AbstractAdvisorAutoProxyCreator.setBeanFactory
 *                          2。applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName)
 *                          3。invokeInitMethods(beanName, wrappedBean, mbd);执行自定义的初始化方法
 *                          4。applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName) 执行所有后置处理器的after...方法
 *                     4. BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator.class) 创建成功--->aspectJAdvisorFactory 也创建成功
 *                   5。把beanPostProcessor创建完注册到容器中：
 *                      beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
 *================================以上是创建AnnotationAwareAspectJAutoProxyCreator 的过程=======================================
 *                   6。创建省下来单实例 finishBeanFactoryInitialization(beanFactory);
 *                      1。 便利获取容器中所有的bean，一次创建对象 getBean
 *                           getBean -->doGetBean()
 *                      2. 创建bean
 *                            1。先从缓存中获取bean，如果存在返回，如果不存在才创建，创建好了会缓存
 *                            2。createBean ;创建bean:AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先吹返回bean的实例
 *                            【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *                            【InstantiationAwareBean是再创建Bean实例前后先尝试用后置处理器返回对象】
 *                                1。 resolveBeforeInstantiation(beanName,mbdType);
 *                                    希望返回一个代理对象，果能返回对象就return，如果不能则doCreateBean(beanName,mdbType,args)
 *                                        1。 bean=applyBeanPostProcessorsBeforeInstantiation()
 *                                            拿到所有后置处理器，如果时InstantiationAwareBeanPostProcessor;就执行
 *                                            if (bean !=null) {
 *                                                bean = applyBeanPostProcessorsAfterInitialization(bean,beanName);
 *                                            }
 *                                       2。 doCreateBean(beanName,mbdToUse,args)真正的去创建一个bean实例
 *
 *                                2。
 *
 *********** AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】的作用：
 * 1。 每一个bean创建实例之前，调用postProcessBeofreInstantiation()
 * 2。 关心MathCalculator何LogAspect的创建
 *      1。 判断当前bean是否再advisedBeans中(保存了所有需要增强的bean)
 *      2。  判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean.
 *           或者是否是切面@Aspect注解
 *      3。 是否需要跳过
 *           1。 获取候选的增强器(切面里面的通知方法)【List<Advisor> candidateAdvisors】
 *               每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor:
 *                判断每一个增强器是否是AspectJpointcutAdvisor类型的，返回true
 *           2。 永远返回false
 *3。 创建对象
 * postPrcessAfterInitialization:
 *          return wrapIfNecessary(bean,beanName,cacheKey);//包装如果需要的情况下
 *          1. 获取当前bean的所有增强器方法(通知方法)
 *                  1。找到候选到的所有增强器 (找那些通知方法是需要切入当前bean方法的)
 *                  2。获取能再当前bean使用的增强器
 *                  3。给增强器排序
 *          2。保存当前bean的advisedBeans
 *          3。如果当前bean需要增强，创建当前bean的代理对象
 *                 1。获取所有增强器(通知方法)
 *                 2。保存到proxyFactory
 *                 3。创建代理对象
 *                     JdkDynamicAopProxy(config);jdk动态代理
 *                     ObjeneisCglibAopProxy(config);cglib的动态代理
 *           4。给容器中返回当前组建使用cglib增强了代理对象
 *           5。 以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程。
 *
 * 4。 目标方法执行：
 *     容器中保存了组建的代理对象(cglib增强后得对象)，这个对象里面保存乐详细信息（比如增强器、目标对象，xxx）：
 *          1。CglibAopProxy.intercept();拦截目标方法的执行
 *          2。根据proxyFactory对象获取拦截器链：
 *              List<object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice
 *                    1。List<Object> interceptorList保存所有拦截器5
 *                       一个默认的ExposeInvocationInterceptor 和4个增强器：
 *                    2。便利所有的增强器，将其转为Interceptor ：
 *                     registry.getInterceptors(advisor);
 *                    3。将增强器转为List<MethodInterceptor>
 *                        如果时MethoInterceptor，直接将入到集合中
 *                        如果不是，使用AdvisorAdapter转回MethodInterceptor
 *
 *          3。 如果没有拦截器链，直接执行目标方法；
 *              拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 *          4。如果有拦截器链，把需要执行的目标对象，目标方法、拦截器链等信息传入一个CglibMethodInvocation对象
 *              并调用Object retVal = mi.proceed()
 *          5。拦截器链的触发过程：
 *              1。如果没有拦截器直接执行目标方法，或者拦截器的索引何拦截器数组-1大小一样（）
 *              2。链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成以后再来执行；
 *              拦截器链的机制，保证通知方法与目标方法的执行顺序
 *
 * 总结：
 * 1。@EnableAspectJAutoProxy 开出去AOP功能
 * 2。@EnableAspectJAutoProxy会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 * 3.AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
 * 4。容器的创建过程：
 *      1。registerBeanPostProcessors()注册后置处理器创建AnnotationAwareAspectJAutoProxyCreator
 *      2。finishBeanFactoryInitialization()初始化剩下的单实例bean
 *          1。创建业务逻辑组件和切面组件
 *          2。AnnotationAwareAspectJAutoProxyCreator
 *          3。组件创建完之后，判断组建是否需要增强
 *              是，切面的通知方法，包装成增强器（Advisor）
 * 5。执行目标方法
 *      1。代理对象执行目标方法
 *      2。CglibAopProxy.intercept()
 *          1。得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 *          2。利用拦截器的链式机制，依次进入每一个拦截器进行执行
 *          3。效果：
 *              正常执行：前置通知--》目标方法--》后置通知--》返回通知
 *              出现异常：前置通知--》目标方法--》后置通知--》异常通知
 *
 *
 *
 *
 *
 *          。。。
 *
 *
 */

@EnableAspectJAutoProxy //开启基于注解的aop模式 之家 spring-aspects jar包即可
@Configuration
@ComponentScan("com.syy.aop")
public class MainConfigAOP {

    //业务逻辑类加入到容器中
    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogSpringAspect logSpringAspect(){
        return new LogSpringAspect();
    }

}
