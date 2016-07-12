# druid-demo

## 开放的接口

#### 查询通过城市名查询城市信息的接口
URL: localhost:8081/druid-demo/info/city/{cityname}
Result: json,城市信息

#### 后台执行多线程查询数据库任务的接口
URL: localhost:8081/druid-demo/info/job
Result: text,执行信息

#### druid监控页面
URL: localhost:8081/druid-demo/druid