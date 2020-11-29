

new AnnotationConfigApplicationContext(MainConfigExt.class);
    this();
    register(annotatedClasses);
    refresh();Spring容器的refresh() 【创建刷新】
        prepareRefresh();
            initPropertySources();初始化一些属性设置，自定义
            getEnvironment().validateRequiredProperties();属性校验
            this.earlyApplicationEvents = new LinkedHashSet<ApplicationEvent>();保存容器中的一些事件
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
            refreshBeanFactory();【创建beanFactory】
                GenericApplicationContext.refreshBeanFactory
                    this.beanFactory = new DefaultListableBeanFactory();无参构造器创建了一个beanFactory
                    设置id
            ConfigurableListableBeanFactory beanFactory = getBeanFactory();
                return this.beanFactory;返回刚才的GenericApplicationContext创建的 DefaultListableBeanFactory
        prepareBeanFactory(beanFactory);//设置beanFactory
            设置类加载器、表达式...等等
            beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));添加各种Aware实现组件
            设置忽略的自动装配的接口EnvironmentAware ...等等
            beanFactory.registerResolvableDependency()设置可以解析的自动装配，我们能直接在任何组件中自动注入：BeanFactory ResourceLoader ApplicationEventPublisher ApplicationContext
            beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
            // Detect a LoadTimeWeaver and prepare for weaving, if found. 添加编译时的AspectJ支持
            // Register default environment beans.
        postProcessBeanFactory(beanFactory);// Allows post-processing of the bean factory in context subclasses.
            子类通过重写这个方法来再BeanFactory创建并准备完成以后做进一步的设置
===============================================以上是BeanFactory的创建与准备=============================================================================
		invokeBeanFactoryPostProcessors(beanFactory);// Invoke factory processors registered as beans in the context.
		    两个接口：
		    //一先执行 BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry方法
		    PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());//执行BeanFactoryPostProcessor方法
		    String[] postProcessorNames =beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);//// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
		    for循环 beanFactory.isTypeMatch(ppName, PriorityOrdered.class)//实现优先级的postProcessor放到currentRegistryProcessors，同时添加到 processedBeans
		    sortPostProcessors(currentRegistryProcessors, beanFactory);//排序
            registryProcessors.addAll(currentRegistryProcessors);//加入到registryProcessors
            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);//执行方法postProcessBeanDefinitionRegistry
            currentRegistryProcessors.clear();
            for 循环!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)//实现Ordered排序的postProcessor放到 currentRegistryProcessors，同时添加到 processedBeans
            sortPostProcessors(currentRegistryProcessors, beanFactory);//同样按上面的过程排序
            registryProcessors.addAll(currentRegistryProcessors);//加入
            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);//执行方法
            currentRegistryProcessors.clear();
            最后还是for 循环 然后执行 没有实现 PriorityOrdered Ordered接口的postProcessor执行方法postProcessBeanDefinitionRegistry
            ...
            //再执行第二个接口 BeanFactoryPostProcessor.postProcessBeanFactory 方法
            invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
            invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
            else: invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
            
            //还是第二个接口 BeanFactoryPostProcessor.postProcessBeanFactory 方法与上一个接口一样的
            String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
            先 PriorityOrdered实现的BeanFactoryPostProcessor 处理(已经在beanFacotory中的就不再处理)
            
            // First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
            // Next, invoke the BeanFactoryPostProcessors that implement Ordered.
            // Finally, invoke all other BeanFactoryPostProcessors.
        
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(beanFactory);//普通bean的后置处理器 BeanPostProcessors(前面是定义注册类、工厂类的后置处理器)
            registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory)
                PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this)
                    //获取所有的postProcessor 但实现或继承BeanPostProcessor的执行时机顺序不一样
                    String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
                    与前面一样 PriorityOrdered 、 Order 、 普通 先后顺序执行，最后还有个internalPostProcessors(MergedBeanDefinitionPostProcessor接口的)
                    // First, register the BeanPostProcessors that implement PriorityOrdered.
                    // Next, register the BeanPostProcessors that implement Ordered.
                    // Now, register all regular BeanPostProcessors.
                    // Finally, re-register all internal BeanPostProcessors.
                    // moving it to the end of the processor chain (for picking up proxies etc).
                    【最后还添加了一个ApplicationListenerDetector的探测器】
                    beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
        // Initialize message source for this context.
        【初始化MessageSource组件（做国际化功能、消息绑定、消息解析）（可以去除国际化配置文件中的key的值，能按照区域信息获取）】
        initMessageSource();
            ConfigurableListableBeanFactory beanFactory = getBeanFactory();
            是否有id="messageSource"组件，
                如果有简单处理赋值设置
                如果没有则自己创建一个返回
                    DelegatingMessageSource dms = new DelegatingMessageSource();
                    dms.setParentMessageSource(getInternalParentMessageSource());
                    this.messageSource = dms;
                    beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);
        // Initialize event multicaster for this context.
        【多播器（派发器）】
        initApplicationEventMulticaster();
            ConfigurableListableBeanFactory beanFactory = getBeanFactory();
            容器中是否有id="applicationEventMulticaster"组件
                有赋值
                无自己创建一个 并注册到beanFactory中
                    this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
                    beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
        // Initialize other special beans in specific context subclasses.
        【留给子容器子类重写】
        onRefresh(); 
        
        // Check for listener beans and register them.
        【注册监听事件】
        registerListeners();
            for (ApplicationListener<?> listener : getApplicationListeners())
                getApplicationEventMulticaster().addApplicationListener(listener)
            String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
            getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
            // Publish early application events now that we finally have a multicaster...
            Set<ApplicationEvent> earlyEventsToProcess = this.earlyApplicationEvents;
            this.earlyApplicationEvents = null;
                getApplicationEventMulticaster().multicastEvent(earlyEvent);
        
        // Instantiate all remaining (non-lazy-init) singletons.
        【初始化所有剩下的单实例】
        finishBeanFactoryInitialization(beanFactory);
            // Initialize conversion service for this context.
            // Register a default embedded value resolver if no bean post-processor
            		// (such as a PropertyPlaceholderConfigurer bean) registered any before:
            		// at this point, primarily for resolution in annotation attribute values.
            // Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
            // Stop using the temporary ClassLoader for type matching.
            // Allow for caching all bean definition metadata, not expecting further changes.
            // Instantiate all remaining (non-lazy-init) singletons.
            【实例化剩下的单实例bean】
                List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);//获取所有的bean名
                // Trigger initialization of all non-lazy singleton beans...
                【先初始化所有非懒加载的单实例bean】
                获取bean定义信息，看 是否：不是抽象类、单实例、非懒加载
                    是，则判定是不是FactoryBean类型
                        是工厂baen，是则要校验是否许可的系统生成bean，否则还是使用getBean(beanName)
                        不是工厂bean：getBean(beanName);
                        【断点追进去bean的创建、实例化】
                            doGetBean(name, null, null, false);
                                // Eagerly check singleton cache for manually registered singletons.
                                Object sharedInstance = getSingleton(beanName);
                                //看是缓存中否被创建过该单实例bean（所有被创建的单实例bean都会被缓存起来)
                                如果拿到：
                                    
                                如果没拿到，则开始创建该单实例，并放入到缓存中
                                    // Check if bean definition exists in this factory.
                                    markBeanAsCreated(beanName);标记beanName已经被创建，让其他多线程注意
                                    // Guarantee initialization of beans that the current bean depends on.
                                    【拿到bean的所有依赖信息，有依赖，则先把依赖的bean先getBean()出来，反复套娃】
                                    // Create bean instance. 依赖处理完，开始创建实例
                                        createBean(beanName, mbd, args); 调用一个createBean
                                            Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
                                            // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
                                            【这里非常重要，给代理类的bean一个机会去改变bean】
                                            Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
                                                InstantiationAwareBeanPostProcessors 提前执行
                                                bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
                                                        Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);执行我postProcessBeforeInstantiation方法了，并返回对象
                                                        return result;
                                                bean不为null呢：
                                                bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                                                        //执行postProcessAfterInitialization
                                                        result = beanProcessor.postProcessAfterInitialization(result, beanName);
                                                        return result;
                                            如果没有拿到代理对象，则：
                                                Object beanInstance = doCreateBean(beanName, mbdToUse, args);【断点进去看看】
                                                    // Instantiate the bean.
                                                    instanceWrapper = createBeanInstance(beanName, mbd, args);
                                                            // Make sure bean class is actually resolved at this point.
                                                            Class<?> beanClass = resolveBeanClass(mbd, beanName);
                                                            if (mbd.getFactoryMethodName() != null)  {//工厂里有这么个属性对象，则工厂方法构造(像Cat是@Bean注入的)
                                                                return instantiateUsingFactoryMethod(beanName, mbd, args);
                                                                        return new ConstructorResolver(this).instantiateUsingFactoryMethod(beanName, mbd, explicitArgs);
                                                                                String factoryBeanName = mbd.getFactoryBeanName();//拿到工厂名，例如@Configuration配置的在前面就称为工厂bean了，里面属性名有@Bean的对象Cat
                                                                                factoryBean = this.beanFactory.getBean(factoryBeanName);//从当前父类工厂Bean拿到其里面的工厂bean(@Configuration的)
                                                                                ......好大一大堆
                                                                                minNrOfArgs = resolveConstructorArguments(beanName, mbd, bw, cargs, resolvedValues);//debug进去走的这个
                                                                                ......
                                                                                argsHolder = createArgumentArray(beanName, mbd, resolvedValues, bw, paramTypes, paramNames, candidate, autowiring);
                                                                                .....
                                                                                beanInstance = this.beanFactory.getInstantiationStrategy().instantiate(mbd, beanName, this.beanFactory, factoryBean, factoryMethodToUse, argsToUse);
                                                                                        里面懒得追了
                                                                                .....
                                                                                bw.setBeanInstance(beanInstance);
                                                                                return bw;
                                                            }
                                                    // Allow post-processors to modify the merged bean definition.
                                                    【调用MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(mbd, beanType, beanName)】
                                                    applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
                                                        MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(mbd, beanType, beanName)
                                                    // Eagerly cache singletons to be able to resolve circular references
                                                        // even when triggered by lifecycle interfaces like BeanFactoryAware.
                                                    
                                                    // Initialize the bean instance.
                                                    【前面是构造创建，以及执行了before类的Processor方法，现在开始设置属性】
                                                    populateBean(beanName, mbd, instanceWrapper);
                                                        属性赋值之前拿到所有后置处理器
                                                        BeanPostProcessor bp : getBeanPostProcessors()
                                                            InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp
                                                            并执行 postProcessAfterInstantiation
                                                        又拿到所有后置处理器：
                                                        BeanPostProcessor bp : getBeanPostProcessors()
                                                            InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                                                            【属性赋值】
                                                            pvs = ibp.postProcessPropertyValues(pvs, filteredPds, bw.getWrappedInstance(), beanName);
                                                        【最后才是真正的属性setter方法赋值】
                                                        applyPropertyValues(beanName, mbd, bw, pvs);
                                                                ......里面一大段 略
                                                    【bean的初始化】
                                                    exposedObject = initializeBean(beanName, exposedObject, mbd);
                                                        【Aware接口】
                                                        invokeAwareMethods(beanName, bean);//实现xxxAware接口的方法执行
                                                            BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
                                                    【BeanPostProcessor接口】
                                                    wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
                                                        【所有的后置处理器触发 postProcessBeforeInitialization接口方法】
                                                        result = beanProcessor.postProcessBeforeInitialization(result, beanName);
                                                    【真正执行初始化】
                                                    invokeInitMethods(beanName, wrappedBean, mbd);
                                                        是否InitializingBean接口实现bean
                                                        是否自定义的初始化initalizition
                                                    【后置处理器的 PostProcessorsAfterInitialization方法】
                                                    wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
                                                    
                                                    // Register bean as disposable.
                                                    【注册bean的销毁方法】
                                                    registerDisposableBeanIfNecessary(beanName, bean, mbd);
                                                    return exposedObject;
                                                return beanInstance;
                                            【addSingleton(beanName, singletonObject);】
                            将创建的BEan添加到缓存中SingletonObjcts
                        IOC容器就是这些MAP，很多的MAP里面保存了单实例Bean，环境信息
            // Trigger post-initialization callback for all applicable beans...
            【后置处理器的回调 afterSingletonsInstantiated()方法 】
        // Last step: publish corresponding event.
        finishRefresh();
            // Initialize lifecycle processor for this context.
            【初始化何生命周期有关的后置处理器：LifecycleProcessor,默认从容器中是否有，没有自己创建一个】
            initLifecycleProcessor();
                写一个LifecycleProcessor实现类可以再BeanFactory重写 onRefresh() onClose()方法
            // Propagate refresh to lifecycle processor first.拿到前面定义的生命周期处理器(BeanFactory)，回调onRefresh()
            getLifecycleProcessor().onRefresh();
            // Publish the final event.发布容器刷新完成事件
            publishEvent(new ContextRefreshedEvent(this));
            // Participate in LiveBeansView MBean, if active.
            LiveBeansView.registerApplicationContext(this);
=======总结============
1。spring容器再启动的时候，贤惠保存所有注册进来的ben定义信息
    1。xml注册bean
    2。注解注册bean
2。spring容器会合适的时候创建这些bean
    1。用到这个bean的时候，利用getBean创建bean，创建好以后保存再容器中
    2。统一创建剩下的所有bean的时候，finishBeanFactoryInitialization()
3。后置处理器：
    1。每个bean的创建完成，都会使用各种后置处理器进行处理，来增强bean的功能
        AutowiredAnnotationBeanPostProcessor:处理自动注入
        AnnotationAwareAspectJAutoProxyCreator:来做AOP功能
        xxx...
        增强的功能注解：
        AsyncAnnotationBeanPostProcessor
        ...
4。事件驱动模型：
    ApplicationListener: 事件监听
    ApplicatonEventMulticaster:时间派发器（多播器)
                                                    
                                                    
                                                    
                                                            
                                                        
                                                    
                                                
                                
                                
                
        
        
                    
             
        
        
            
        