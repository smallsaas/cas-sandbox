## 1.CAS SSO

### 各模块说明

|模块|说明|详细文档|
|:--:|:--:|:--:|
|cas-client|客户端 负责保护app资源，未登录情况下访问受到保护资源 会重定向到服务端进行登录|[客户端文档](./cas-client/README.md)|
|cas-management-overlay|管理端 负责服务的注册和管理|[管理端文档](./cas-management-overlay/README.md)|
|cas-overlay-template|服务端 登入入口 票据签发中心|[服务端文档](./cas-overlay-template/README.md)|

### 数据库说明

```
  原本的cas默认采用的是json格式的服务注册方式，这里将json改为数据库的形式进行服务注册。
  下面的表格是cas所用到的数据库的说明。
    - 是否自动生成表示：当配置文件 cas.serviceRegistry.jpa.ddlAuto
      为 create 或 create-drop的时候会在 服务端 管理端 启动的时候创建
    - 除了t_sys_user表以外，其他表的缺失会影响cas的正常启动
```

|数据表|是否自动生成|说明|
|:--:|:--:|:--:|
|RegexRegisteredService|是|主要的服务注册表|
|RegexRegisteredServiceProperty|是|待测试|
|RegisteredService_Contacts|是|待测试|
|RegisteredServiceImpl_Props|本地可以，放到服务器上不会自动生成，待确定|待测试|
|RegisteredServiceImplContact|是|待测试|
|SamlMetadataDocument|是|待测试|
|t_sys_user|否|用户表，登录用，非cas自带|



    

## 2. KPI记录

### 2.1 数据表设计

#### 0x1. 提交记录表（kpi_coding_commit_record ）

| **Field Name** | **Type**     | **Description**                                              |
| :------------- | :----------- | :----------------------------------------------------------- |
| id             | bigint(20)   | 记录ID                                                       |
| org_id         | bigint(20)   | 用于隔离的组织ID，由crud-plus维护<span style="color:blue">（保留便于扩展）</span> |
| org_tag        | varchar(100) | 用于隔离的组织标识<span style="color:blue;">（保留便于扩展）</span> |
| module_id      | bigint(20)   | 模块ID                                                       |
| module_name    | varcahr(100) | 模块名称                                                     |
| commit_id      | varchar(256) | 提交ID                                                       |
| commit_author  | varchar(64)  | 作者                                                         |
| commit_comment | varchar(512) | 评论内容                                                     |
| commit_lines   | int(20)      | 提交行数                                                     |
| is_deleted     | smallint     | 逻辑删除 默认值0, 1为删除标记                                |
| create_time    | datetime     | 记录创建时间，crud-plus框架保留字段**,**自动忽略更新         |

#### 0x2. 开发人员信息表（kpi_coding_submitter）

| **Field Name** | **Type**     | **Description**                                              |
| :------------- | :----------- | :----------------------------------------------------------- |
| id             | bigint(20)   | 人员ID                                                       |
| org_id         | bigint(20)   | 用于隔离的组织ID，由crud-plus维护<span style="color:blue">（保留便于扩展）</span> |
| org_tag        | varchar(100) | 用于隔离的组织标识<span style="color:blue;">（保留便于扩展）</span> |
| commit_author  | varchar(100) | 开发者名称                                                   |
| commit_email   | varchar(100) | 开发者邮箱                                                   |
| user_id        | bigint(20)   | 系统用户ID                                                   |
| create_time    | datetime     | 开发人员信息创建时间，crud-plus框架保留字段**,**自动忽略更新 |

#### 0x3. 项目信息表（kpi_coding_project）

| **Field Name** | **Type**     | **Description**                                              |
| :------------- | :----------- | :----------------------------------------------------------- |
| id             | bigint(20)   | 项目ID                                                       |
| org_id         | bigint(20)   | 用于隔离的组织ID，由crud-plus维护<span style="color:blue">（保留便于扩展）</span> |
| org_tag        | varchar(100) | 用于隔离的组织标识<span style="color:blue;">（保留便于扩展）</span> |
| name           | varchar(100) | 项目名称                                                     |
| client_id      | bigint(20)   | 客户ID                                                       |
| desc           | text         | 项目描述                                                     |
| is_deleted     | smallint     | 逻辑删除 默认值0, 1为删除标记                                |
| create_time    | datetime     | 项目信息创建时间，crud-plus框架保留字段**,**自动忽略更新     |

#### 0x4. 模块信息表（kpi_coding_module）

| **Field Name** | **Type**     | **Description**                                              |
| :------------- | :----------- | :----------------------------------------------------------- |
| id             | bigint(20)   | 模块ID                                                       |
| org_id         | bigint(20)   | 用于隔离的组织ID，由crud-plus维护<span style="color:blue">（保留便于扩展）</span> |
| org_tag        | varchar(100) | 用于隔离的组织标识<span style="color:blue;">（保留便于扩展）</span> |
| name           | varchar(100) | 模块名称                                                     |
| project_id     | bigint(20)   | 仓库ID                                                       |
| project_name   | varchar(100) | 模块代码项目名称                                             |
| repo_url       | varchar(100) | 仓库地址                                                     |
| is_deleted     | smallint     | 逻辑删除 默认值0, 1为删除标记                                |
| create_time    | datetime     | 模块信息创建时间，crud-plus框架保留字段**,**自动忽略更新     |
