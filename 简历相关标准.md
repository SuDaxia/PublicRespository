一. 先看招聘网上的各种要求

1.简历：  先写，反向准备学习
  1. JAVA基础
  2. Spring SSM
  3. 中间件
  4. 数据库
  5. 分布式/微服务
  6. 加分项
  
1.1 JAVA基础：
  1. java se
  2. 数据结构&算法 - [学科] !! 曲线救国，jva的集合体系!
        设计模式，阿里面试 java的collection集合、map的讲解，排序算法
  3. IO:网络通信 BIO NIO Netty epoll/select
  4. JUC: 线程/锁/队列/线程池
  【可以不说： JVM 小到只需要知道参数，大的话要深入linux内核 ，早期不需要深入，当找六七十万年薪就要深入内核了】
  
1.2 Spring
  1.容器 IOC/DI控制反转/依赖注入，容器 对象 bean 放进去、取出来，怎么控制反转；放进去的时候有DI依赖，AOP 到springframework源码里面大框架进程，asm
  2. AOP切面
  【】源码的 需要配合设计模式、功能、底层

1.3 中间件 （涉及宽度技术选型，中间件开发素养：有一个项目，文件存哪里，FTP/SFTP、DB、Redis、hbase、hdfs、kafka、es、mongodb、1.文件：interface 2.映射，最后别人用SDK救星）
  1.缓存
  2.队列
  3.协调
  4.文件
  5.搜索
  ....
  大数据组件之类的东西ODPS MaxCompute阿里 SQL HIVE
  ...
  redis
  zookeeper
  kafka
  mq
  fastdfs
  架构能力，技术选型
  ...
  中间件分类，缓存类，存储类，分析类存储系统
  '''
  
1.4 数据库
  公开课：文件，db，redis，hbase

1.5 分布式/微服务 
  问题：
    CAP
    PAXOS
    BASE
    2PC
    3PC
  
  redis事务，一致性（强一致性、最终一致性）、可用性、容忍性、副本、分片
  
  AKF
  
  为什么集群：
  1. 单点 （单点故障潜在风险）
  2. 压力 （读写分离、分片）
  
  主从复制/专业叫主备模式，外面写永远访问的主节点；数据以什么方式存储的，全量数据；读写分离
  业务划分：按业务做redis实例划分，一个业务挂了，不影响另外的业务
  倾斜：某个业务数据一定会很大，然后一类业务，同一类数据，对数据做sharding分片
  
1.6 加分项
  发展方向：
    1. 互联网架构师(主)+大数据工程师，架构师主线，大数据复现，如果已经转到大数据工程师，那也可以往AI算法底层弄弄
        适合曾经具备java开发经验，为了继续提升，开拓更大的职场晋升空间
    2. 大数据工程师(必须项)+AI算法工程师，
        适合曾经有过传统数仓+java开发，研究生是机器学习、人工智能的学员，多一个技术栈绑定，无论知识视野还是职场晋升空间双赢！
    3. 互联网架构师+大数据工程师+AI算法工程师
        绝对的顶级技术专家、CTO、技术合伙人必备技术栈，获取人生选择权的起点
  



  【课外是马士兵教育课程表啊注意：
  网络到分布式
  24 高并发负载均衡：网络协议原理
  25 高并发负载均衡：LVS的DR,TUN,NAT模型推导
  26 高并发负载均衡：LVS的DR模型实验搭建
  28 高并发负载均衡：机遇keepalive的LVS高可用搭建
  43 redis 介绍及NIO原理介绍
  44 redis的string类型&bitmap
  45 redis的list\set\hash\sorted\set\skiplist
  48 redis的消息订阅、pipeline、事务、modules、布隆过滤器、缓存LRU
  49 redis的持久化RDB、fork、copywrite、AOF、RDB&AOF混合使用
  50 redis的集群、主从复制、CAP、PAXOS、cluster分片集群01
  53 redis的集群、主从复制、CAP、PAXOS、cluster分片集群02
  54 redis并发 spring data redis 连接 序列化 high/low api
  55 zookeeper介绍，安装，shell cli使用，具成本概念验证
  56 zookeeper原理知识，paxos、zab、角色功能、API开发基础
  57 zookeeper案例：分布式配置注册发现、分布式锁、ractive模式编程
  
  】
  

1.6 加分项
  
  
  
  
  
 ##################### 课外
 自主学习能力，apache官网，github上的官网与源码
  
 社会是不公平的
 
 资源倾斜。。。。（自己条件好，不要浪费资源，）
 
 AI可以学偏应用，如果已经在资源倾斜队列了，如果硕士可以试着去弄，不要胆怯，勇敢竞争，迎难而上！！！！！
 初学！快速上手！接受后端开发 OA SpringBoot SpirngCloud，刨根稳定：带着问好去的。。。
 
 放低姿态：实习，低薪（技术不能拖后腿）
 
  
