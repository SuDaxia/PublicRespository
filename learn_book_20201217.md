# 笔记

### 马士兵 哔哩哔哩 JVM调优 6 为什么百万订单系统???

Golang语言 是google是 替代C的,不小心要感PHP,PHP要被很多语言干,不要碰

java的多线程要做很多操作

Go语言主要是本身就用关键字标示来实现纤程\协程;主要是语言简洁

非主流Ruby不要学,都要死了

#### java参数
java 回车,标准参数,十几二十个

java -X 回车,非标准参数,有些版本支持,有些版本不支持,但大多还是支持的

真正的调优参数是
java -XX 回车,看不到任何提示的东西

java -XX:+PrintFlagsFinal -version 回车
java -XX:+PrintFlagsFinal -version |wc -l  #七百多个参数

什么是调优?
1.根据需求进行JVM规划和预调优
2.优化运行JVM运行环境(慢\卡顿)(怎么才能定位一个系统的瓶颈?压测)
3.解决JVM运行过程中出现的各种问题(OOM)

调优,从规划开始
.调优\从业务逻辑开始,没有业务场景的调优都是耍流氓
无监控(压力测试\能看到结果),不调优
步骤:
  1.熟悉业务场景(没有最好的垃圾回收器,只有最合适的垃圾回收器)
    1.响应时间\停顿时间[CMS G1 ZGC](需要给用户作响应)
    2.吞吐量=用户时间/(用户时间+GC时间)[PS]
  2.选择回收器组合
  3.计算内存需求(经验值 1.5G 16G)
  4.选定CPU(越高越好)
  5.设定年代大小\升级年龄
  6.设定日志参数
    1.-Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log -XX:+UseGClogFileRotation -XX:NumberOfGCLogFiles=$ -XX:GClogFileSize=20M -XX:+PrintGCCause
    2.或者每天产生一个日志文件
  7.观察日志文件
  
#### 案例1: 垂直电商最高每日百万订单,处理订单系统需要什么样的服务器配置?
这个问题比较业余,因为很多不同的服务器配置都支持 1.5G 16G
1小时3600000集中时间段,100个订单/秒. (找一个小时的高峰期 1000单/秒)
经验值:
非要计算:一个订单产生需要多少内存? 512k * 1000  500M内存
专业一点的问法:要求响应100ms
压到!

#### 案例2: 12306遭遇春节大规模抢票应该如何支撑?
12306应该是中国并发量最大的秒杀网站
号称并发量100w最高
CDN -> LVS --> NGINX --> 业务系统-->每台机器1w并发(10K问题) 100台
普通电商订单-->下单-->订单系统(IO)减库存-->等待用户付款
12306的一种可能的模型:下单-->减库存和订单(readis kafka)同时异步进行-->等付款


################
GC日志

top

jinfo pidxx

jstate -gc pidxx  flushtimemsxxx

jstack非常重要的工具
jstack 把当前系统中所有线程都列出来

cpu高升,一定要抓出来,哪个线程不停的占据cpu,到底是系统线程\业务线程还是GC线程

如果发现是系统线程

/**
 * 一个案例：
 * 1。测试代码
 * 2。java -Xms20M -Xmx20M -XX:+PrintGC base.java.mashibing.tunning.jvm.T15_FullGC_Problem01
 * [Xms Xmx 设置一样，防止它动态调整中消耗过多资源，还有抖动]
 * 3. 一般是运维团队首先收到报警信息（CPU Memory）
 * 4。top命令观察到问题：内存不断增长CPU占据率居高不下
 * 5。top -Hp 观察进程中的现成，哪个线程CPU和内存占比高
 * 6。jps定位具体java进程
 * jinfo pid
 jstat -gc pid
 * jstack pid 定位线程状况，把所有的线程都列出来重点关注 WAITING BLOCKED 【cpu飙高这里调用了哪些方法，不断消耗资源，大多是内存回收不掉，内存泄漏，直至内存溢出】
 jmap 把整个堆内存 dump【生产千万别dump和图形化界面远程连接】，然后利用可视化的工具jvm visual，一眼看出，但实际千万不要说生产环境这么干，真正大厂生产环境几十G，dump业务还搞不搞了
 顶多说测试环境压测，或者负载均衡隔离环境中用一台机器[马上就会问怎么做高可用负载均衡的]，或者tcp连接【tcp down？？】复制copy一份打到专用的环境测试。不能影响生产正在进行的业务。
 jmap也可以直接用命令行，直接看到 jmap -histo pid 把进程里面哪个类生成多少个占据多少个，注意head -n 只看前几十个
 定位问题可以很快，但决绝问题很难
 【其它工具还有 mat 等等】
 【终极推荐的工具就是 arthas--packaging-3.1.4-bin】
 * eg:
 * waiting on <0x0000000088ca3310>(a java.lang.Object)
 * 假如有一个进程中有100个现成，很多线程都是在waiting on <xxx> ,一定要找到是哪个线程持有这把锁
 * 怎么找？搜索jstack dump的信息，找<xxx>, 看哪个线程持有这把锁RUNNABLE
 * 作业：1：写一个死锁程序，用jstack观察 2：写一个程序，一个线程持有锁不放，其他线程等待
 *
 * 7。为什么阿里规范里规定，线程的名称(尤其是线程池) 都要写有意义的名称
 * 怎么样自定义线程池里的线程名称？（自定义ThreadFactory）
 *
 * 8。
 *
 */
  
  【终极推荐的工具就是 arthas--packaging-3.1.4-bin】使用【阿里大厂，真正生产级别使用的工具】
  java -jar arthas-boot.jar 启动命令
  输入正在运行的进程，序号
  在里面敲help命令
  dashboard  命令行模拟的观察界面
  thread 查看每个线程的情况
  thread -b 直接帮你查出 死锁blocking的线程，比自己手工的去查看一个对象握锁导致非常多等待要容易多
  thread 序号id
  heapdump 也是导堆
  【最近面试图：如何定位一个系统的瓶颈】
  【情况一：单机程序，压测，专门的条有工具jp filer??  或这个】【情况二：分布式环境，全链路】
  arthas trace 类.方法名 带参数的呢？查看help，一步步往下定位那个
  
  原理：java agent api 专门的切面，所以支持上面的各种调优工具
  
  凡是好看的特别可视化的，一般生产最好别用。
  
  arthas骚操作，吊打面试官？？？
  jad 是个java a decompare 反编译，这只是小一，用来确认版本，看是不是用错了，大公司版本太多了
  还有一个骚操作
  生产环境应用都还没有停
  编译好正确的那个类.java 得字节码文件之后，用arthas命令 redefine 加载并且定义新的class，替换成功之后，可以发现生产上换成新的了。
  这时紧急情况下，程序bug了，没有时间专门去走打包发布新版本流程了，秒杀活动之类的，直接线上修改提交修改，但生产上没有java吧，要是类特别多呢？哈哈，也是蛮难的
  
  【场景】
  场景一：low的送人头，同时往list不断假对象，别说
  场景二：少说，小米频繁gc，C++转行java频繁GC， JVM 有一个Disable？？GC，不要自己些System.gc，看到finilize()在里面手动释放资源，类消失的比创建的慢，就爆了频繁GC
  场景三：DIstuptor有个可以设置链长度，如果过大，然后对象大，消费完不主动释放，会溢出
  用jvm都会溢出，mycat用蹦过，1.6.5某个临时版本解析sql
  exists的联合sq就导致生成几百万的对象
  
  new 大量线程，会产生 native thread OOM, low送人头
      解决方案：减少堆空间（太TM的low了），预留更多内存产生native thread， jvm内存占物理内存比例50%-80%
      
  近期学生案例SQLlite的类库，批处理的时候会把所有的结果
  几十万条数据，结果久产生了内存溢出，定位上用的是排除法
  上该模块就会出问题
  
  java在线解压以及压缩文件造成的内存溢出
  
  java使用opencv造成的卡顿与缓慢
  
  最容易引起崩溃的报表系统
  
  分库分表所引起的系统崩溃
  
  CLQ引起的 内存泄漏
  
  Hibernate程序不完善写法引起的OOM（ 见Lieber QQ ）
  
  Flanker WX
  
  悬而未决 （Super man QQ）
  
  悬而未决 （天角兽 WX）
  
  周胜 WX
  
  【挑一个场景】
  
  
  调优终极法宝
  第一大法宝，出了问题立即重启，但面试千万别这么说，这是潜规则
  
  
 
  
  
