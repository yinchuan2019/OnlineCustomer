# 使用必读   
- 1.项目说明：可参考 pom.xml中的<modules>部分
- 2.分页查询demo： app-order-server项目 com.app.order.web.DemoOrderController /findPage
- 3.分布式定时任务demo： app-order-server项目 DemoSchedule
 	注：目前由redis实现，支持秒级任务，如果任务执行时间过长（过期时间 > 业务代码的执行时间）
 	最好使用rabbitmq做异步执行，考虑后期切换成xxl-job
 - 4.分布式延时任务demo: 参考 https://www.cnblogs.com/wintercloud/p/10877399.html
 -  5.消息中间件demo（写了direct和topic两种类型的demo）
 
    	（1）消息发布者： app-order-server项目 DemoTopicSender
    		相关配置：1.pom.xml(引入spring-boot-starter-amqp) 2.application.yml（exchange、queue、routingKey） 
    		3.RabbitConfig 
    	（2）消息接收者： app-policy-server项目 com.app.policy.service.demo.mq.DemoQueueListener1、2、3	
   - 6.update乐观锁写法：
   
    	参考 app-basic-server项目 AbuttonResourcesController /updateAbtn 
- 7.读写分离(分包方式：一个包对应一个数据源)

      		1.修改配置文件 application.yml（datasource变更为双数据源）
      		2.将dao和mapper拆分成masterDao、slaverDao和masterMapper、slaverMapper
      		3.添加配置文件 DataSourceMasterConfig（配置master的DruidDataSource、SqlSessionFactory（配置*Mapper.xml路径）、SqlSessionTemplate和DataSourceTransactionManager）
      		4.添加配置文件 DataSourceSlaverConfig同上
- 8.druid监控

              	1.添加 DruidConfig(可以设置黑白名单，账号密码)
              	2.修改 ResourceServerConfig（添加 /druid/**）
              	3.druid监控URL: localhost：项目端口号/druid/login.html admin/admin
              	注：使用过程中，如果出现页面样式不对、用户名密码提示不对、没有权限等奇奇怪怪的问题，可以试下清下浏览器缓存
              	-9.zipkin(内存方式)
              	
                 	1.下载jar: https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
                 	2.命令行启动: java -jar zipkin-server-2.12.2-exec.jar --logging.level.zipkin2=INFO（默认INFO级别可以不设置logging）
                 	3.修改 bootstrap.yml
                 	4.监控页面： http://127.0.0.1:9411
                 	
- 9.javaBean和xml相互转化。在 app-core test里面由例子
- 10
    BeanUtil.replaceBlankStr(T t) 方法
    入参对象，字符串类型字段 空字符串->null  --- 因为有的mapper.xml  只判断null 不判断空。
    
 
- 注意： 
             
               	（1）各项目的dao、mapper.xml、model 均为mybatis-generate代码生成的,开发人员可自行开发
               
               	（2）查看mq队列以及消息消费情况：192.168.21.177:15672	 admin/123456
               
               	（3）查询eureka服务及集群信息：localhost:6761	 root/admin123!
               
               	（4）查看swagger-ui接口信息(前端)：http://localhost:5051/swagger-ui.html#/  	001/1/app-admin/app-admin-secret
               
               	（5）查看swagger-ui接口信息(后端)：http://localhost:5050/swagger-ui.html#/ 	 001/1/app-admin/app-admin-secret
               
               	（6）经验尚浅，如有建议请及时反馈，会及时改善    	
		