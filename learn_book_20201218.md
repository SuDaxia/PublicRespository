### 马士兵 bilibiil 手撕Spring源码

先理解抽象脉络,再填充]细一级的,然后再细一级,不要死扣一个非常非常深的,只有整体都有框架后,再一层层扣细,比较好串联整理,会很自然

----SpringIOC启动到填充bean过程:初始化IOC容器,读取配置文件,解析配置文件,加bean
xml
注解
json
其他配置文件
-------什么是实例化
definitionReader读取,然后反射,
利用BeanDefinition得到bean信息,
通过BeanFactoryPostProcessor---->这个可以对bean为所欲为
PlaceholderConfigurarSupper??这里替换${jdbc.drive}

丢给BeanFactory的Constructor ctor  = new  创建bean, 初始化,属性赋值
-------再执行初始化,再init method
--------完整对象完成

Spring 框架,生态,底层支持,基层,

DefaultListBean

BeanFactory

PostProcessor 增强器\后置处理器,就是进行扩展的
