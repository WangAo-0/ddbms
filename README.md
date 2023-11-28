1. 程序启动，请配置环境变量：
```
   服务器1:
   PORT:9517
   GRPC_PORT:9507
   URL:jdbc:mysql://****:3306/region1
   DB_NAME:root
   PASSWD:*****
   
   服务器2:
   PORT:9518
   GRPC_PORT:9508
   URL:jdbc:mysql://*****:3306/region2
   DB_NAME:root
   PASSWD:*****
   
   服务器3:
   PORT:9519
   GRPC_PORT:9509
   URL:jdbc:mysql://*****:3306/region3
   DB_NAME:root
   PASSWD:*****
   
   参照实验报告创建好数据库和表之后；
   实际运行时，请将***替换成实际的内容；
``` 
2. 测试时请使用以下格式的请求
```
   curl -XPOST -H "Content-type: application/json" http://****:9517/ -d '{"str": "****"}'
   key必须是str，value是实际的sql语句
   请将***替换成实际的内容
```
