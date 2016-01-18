rabbitgift
==========

按照DDD的思想，拆分成若干个Bounded Context。每个bounded context是一个spring boot app。

使用
----
首先去 https://github.com/gyb/dubbo 安装3.0.0-SNAPSHOT版本的Dubbo二进制的包

cd dubbo

mvn clean install -DskipTests


然后运行

mvn clean install


然后分别运行每个服务

cd core/xxx

mvn clean spring-boot:run


最后

cd web

mvn clean spring-boot:run

访问 http://localhost:8080/
