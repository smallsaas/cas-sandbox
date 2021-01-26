## CAS管理端
### 部署
1. 将docker复制到项目文件夹
2. docker-compose build
3. docker-compose up -d 启动容器
4. docker logs -f -t cas-server-manager 查看日志

### 本地运行
根据系统不同运行以下命令（二选一）
1.build.cmd run
2.build.sh.run

### 主要配置说明

```
管理端访问路由
server.context-path=/cas-management
管理端端口
server.port=8080
# cas-management服务地址 部署的时候 此处的localhost改为对应服务器的ip地址
mgmt.serverName=http://localhost:${server.port}
# 语音改中文，但没生效
mgmt.defaultLocale=zh_CN

##
# CAS Server
#
# 需要配置服务器的ip 和 路由
cas.server.name=http://192.168.3.239:8081
cas.server.prefix=${cas.server.name}/cas

#  自定义属性 用于定义返回的字段 也可以在管理端（管理端部署后的页面）设置全返回（所有字段 不限于此处定义）
cas.authn.attributeRepository.stub.attributes.uid=uid
cas.authn.attributeRepository.stub.attributes.givenName=givenName

数据库配置和服务端相同，参考服务端配置流程即可



```
