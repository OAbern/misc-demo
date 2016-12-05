## Redis的简单示例
### 鄙人的[学习笔记](https://www.zybuluo.com/Bern/note/566236)
1. 极其简单的Redis操作代码
2. 使用Redis实现分布式业务锁
- RedisLock照搬我[朋友的代码] (https://github.com/cqbc/Utils/tree/master/util/src/main/java/com/netease/welkin/DB)，自己写单测检验可用
- BizLock自己编写，思路主要是使用Redis事务,正确性待验证[tbd]
- 第三种思路是使用redis的CAS操作incre,待完善[tbd]



---

### 运行步骤

1. 首先确保本机搭建了redis的环境，或者可以更改代码中关于Redis地址的部分；

