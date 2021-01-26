## CAS 客户端

### 部署与运行
按照正常spring-boot项目部署即可。
本地运行 直接运行Amapplication

### 配置说明

```
  cas:
    assertion-filters: /*
    auth-filters: /*
    # 服务端登录地址
    cas-server-login-url: http://localhost:8080/cas/login
    cas-server-url-prefix: http://localhost:8080/cas/
    # 定义认证时候忽略的URL信息，多个可用|分割 支持正则
    ignore-filters: /client/auth/logout
    redirect-after-validation: true
    request-wrapper-filters: /*
    # 受保护的url资源地址   即 此客户端 对应的 app访问地址
    server-name: http://localhost:8081
    sign-out-filters: /*
    use-session: true
    validate-filters: /*
	
	
	
	
cas-custom:
  #登录成功后访问的地址
  success-url: http://localhost:8081/client/auth/test
```

