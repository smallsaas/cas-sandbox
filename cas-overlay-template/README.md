## CAS 服务端

### 运行方式
#### 一. 本地tomcat启动
1.服务端打包后将war包放到tomcat下的webapps下 tomcat会自动解压(重新部署的时候需要删掉 tomcat解压出来的文件)

- 若不希望tomcat自动解压修改conf\server.xml文件

```XML
 <!-- 若不希望tomcat自动解压，此处的Host标签中的 unpackWARs改为false -->
<Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true">

```

2.根据系统不同 运行 bin\startup.bat 或 bin\startup.sh
运行后tomcat会自动加载webapps下的文件

#### 二.官方提供的本地脚本运行（本地没测试通过）
根据系统不同运行 build.cmd run或者build.sh run

#### 三.服务器运行
将war包放入项目中的 docker\server文件夹中
然后将docker里的内容放到服务器上
服务器在项目文件夹中运行docker-compose up -d 即可部署

### 二.配置说明
这里说下常改的几个配置，其他配置直接复制使用即可
带#的基本不用改。
**一般要改数据库 其他基本不用动**

```
1.server.context-path=/cas
配置后登录接口为cas/login
同时会拦截/cas/*路由，客户端设置api的时候不要设置/cas/*的路由，会导致多次重定向的情况

2.server.port=8080
服务器端口设置

3.
# security.basic.enabled=true
# security.user.name=casuser
# security.user.password=
#cas.authn.accept.users=casuser::Mellon
这三个是设置静态登录的时候用的，仅测试用 现在用数据库的方式登录

4.cas.authn.jdbc.query[0] 相关的是在不启用自定义登录认证的情况下 相关的数据库配置。
由于自定义密码策略拓展性不能满足业务需求，已弃用

5.cas.tgc.secure=false
#https给为http相关的配置

6.# 开启识别json文件，默认false
#cas.serviceRegistry.initFromJson=true
#12秒扫描一遍
#cas.serviceRegistry.schedule.repeatInterval=12000
#默认为json注册 会在\WEB-INF\classes\services下出现一个serverName+id的json文件

7.
# Logout配置
# 在CAS 5.3.x版本退出加上service参数，不会自动跳转，需要在此处配置一下
cas.logout.followServiceRedirects=true


8.
### 数据库连接配置
datasource.url=jdbc:mysql://192.168.3.239:3307/cas?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC
datasource.username=root
datasource.password=root
#这个是配置登录时的数据库

9.
##
# Jpa配置
#
cas.serviceRegistry.jpa.user=root
cas.serviceRegistry.jpa.password=root
cas.serviceRegistry.jpa.driverClass=com.mysql.jdbc.Driver
cas.serviceRegistry.jpa.url=jdbc:mysql://192.168.3.239:3307/cas?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC
cas.serviceRegistry.jpa.dialect=org.hibernate.dialect.MySQL5Dialect

#这里配置服务注册通过数据库注册

#设置配置的服务，一直都有，不会给清除掉 ， 第一次使用，需要配置为 create-drop
#create-drop 重启cas服务的时候，就会给干掉
#create  没有表就创建，有就不创建
#none 一直都有
#update 更新
cas.serviceRegistry.jpa.ddlAuto=update
#这项配置的使用顺序：先create启动 之后就可以改成update了，不然会把之前的表覆盖。




```


### 三.其他
1.自定义登录的类在 handler文件夹中的CustomerHandlerAuthentication方法中
config文件夹下的CustomAuthenticationConfiguration配置使用自定义认证的方法
最后在spring.factories加入CustomAuthenticationConfiguration 让spring扫描到完成自定义登录配置

2.自定义登录登录的sql在config中的MysqlConfig类中定义（配置形式的出错，此处直接定义）