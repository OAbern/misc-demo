## Zookeeper的简单示例

- 本Demo采用的Zookeeper版本是r3.4.8

- Zookeeper的官网上给出了一个JAVA的示例程序 [ZooKeeper Java Example](http://zookeeper.apache.org/doc/r3.4.8/javaExample.html) ,在本项目的 **official** 包下copy这份代码，并加入了一些自己理解的注释！

- 由于鄙人的水平有限，不能很好的理解官方给出的实例程序，于是自己模仿编写了一个新的Demo，方便自己理解，程序实现在 **custom** 包下！

- [鄙人的Zookeeper学习笔记地址](https://www.zybuluo.com/Bern/note/444471) 

---

### 运行步骤

1. 首先确保本机搭建了zookeeper的环境，并启动了zkServer。可以参考 [官方文档](http://zookeeper.apache.org/doc/r3.4.8/zookeeperStarted.html) ；

2. 运行custom包下的Main类，输入必要的运行参数hostport(zkServer运行的IP地址和端口号，例127.0.0.1:2181)、zNode（操作的zNode节点的路径）；（官网的Demo运行offcial包下的Exceetor类，其余操作类似，参数不一样）

3. 打开zookeeper程序包bin目录下的zkCli，建立一个zk客户端，对以上的zNode节点进行修改操作；

4. Demo程序会在控制台输出监控信息；
