## Spring-Boot 数据源动态更新


### spring-boot Version  >= 2.1x


>1.首先正常启动项目

>2.访问 http://localhost:8080/getJdbcUrl。会显示当前连接的jdbcUrl。

>3.修改 config.json,更新url,username,password 属性。

>4.curl -X POST http://localhost:8080/actuator/refresh

>5.访问 http://localhost:8080/getJdbcUrl。会显示更新后连接的jdbcUrl。


### Reference Document :

[https://cloud.spring.io/spring-cloud-static/spring-cloud-commons/2.2.1.RELEASE/reference/html/#refresh-scope](https://cloud.spring.io/spring-cloud-static/spring-cloud-commons/2.2.1.RELEASE/reference/html/#refresh-scope)

