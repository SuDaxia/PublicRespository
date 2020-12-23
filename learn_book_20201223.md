
### 马士兵 bilibili Redis、zookeeper、kafka、Nginx、Netty、Epoll、NIO、分布式、Hbase串讲 P5

#### linux的网络操作，就是文件句柄读写
ulimit的使用
ulimit -u 当前用户文件句柄限制(fd的个数，可以在/proc/pidxx/fd中查看)
ulimit -n 当前用户可以开启的线程task限制(task的个数，可以在/proc/pidxx/task中查看它开启的子线程进程task个数，linux中线程和进程是混淆的)

AIO，一个主线程，bind、listen、accept、
BIO，一个主线程，bind,listen,然后每来一个客户端，启动副线程阻塞处理
NIO，一个主线程，bind，有一个注册listen事件，注册accept事件，注册读写事件，然后利用select，调用内核，
    早期，循环select单个fd给内核调用，
中期，（多路复用）将多个fd同时传递给内核，没有fd的重复传递，然后内核要遍历，将有新状态的fd返回，然后程序自己处理
Epoll，然后，内核发展，有空间存储，不必每次遍历，application通知内核开辟内核空间存储fd，然后程序不必每次重复传递已经传递过的fd，只要有新事件触发epoll_wait，就会通知程序
trace命令跟踪 redis的启动过程
  epoll_create()得到文件描述符 fd5
  bind
  listen
  epoll_ctl(fd5,fd6) 把fd6胶乳到fd5监听，得到fd7
  epoll_wait（fd7。。。） 等待新事件状态
  有客户端来连接，得到fd8

引申出redis持久化的两种方式RDB、AOF，前者二进制体积小，重高QPS影响小，后者文件追加命令，fork线程重写AOF文件进行压缩，恢复慢。
bgsave主动fork另起线程持久化，strace可以看到clone

  
nginx也是用的epoll 也可以strace跟踪，县一个进程生成master进程就完了，master进程再生出worker进程，worker处理事件挂了，不影响master再reload等等

然后除了epoll，又谈到了mmap提高读磁盘数据快的原因，
  程序代码栈空间、堆上空间、堆外区间(offheap,unsafe方法开辟byte，addr不需要jvm，unsafe用的底层方法)，堆外空间通过mmap直接与内核共用，内核读取磁盘
  数据挂在mmap空间，程序则直接读mmap空间，不必调内核一点点复制
然后就是零拷贝，不必内核复制到程序，程序再调内核复制到内核再写入磁盘，内核直接告诉kafka偏移量就行，网卡传输过来数据在内核上直接写入磁盘【如果数据要加工，那就没办法避免了】
还有充分利用磁盘顺序读写，顺序空间对齐分块，segement对应底层磁盘区域，满了一次性刷入。








