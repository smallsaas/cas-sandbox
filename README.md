## 1. KPI记录

### 1.1 数据表设计

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
