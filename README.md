# plus

#### 介绍
Java项目：SpringBoot, MyBatis-Plus, Redis, Logback, Hutool, Swagger-UI

* master: 线上
* beta: 测试
* other: 各自分支

#### 使用说明
1. 拉取项目到本地: git clone xxx 
2. 在配置中心配置相关项目的环境配置，如：你自己的 mysql、redis、jenkins连接
3. 系统生成的 API 管理工具 swagger 接口文档地址：<http://localhost:8899//swagger-ui.html>
#### 软件架构
软件架构说明
##### java
* base : 基础类
* common : 公共类
* config : 配置
* constant : 常量，存放固定不变的信息
* dto : 入参对象
* controller ： 对外接口层
* entity : 实体对象，与数据库表一一对应
* exception : 自定义异常
* job ： 定时任务相关
* mapper : dao
* service : 业务层接口
    * impl : 业务逻辑实现类
* utils : 自定义工具
##### resources
* sql : mapper.xml
* templates : 代码生成模板
* application.beta : 测试环境配置
* application.deploy : 线上环境配置
* application.dev : 开发环境配置（默认）
* logback-spring : logback配置
#### 代码自动生成
* /templates/code.propertie 进行数据库配置
* includeTables 配置需要生成代码的相关表
* CodeGenerator类main方法进行执行生成基础代码


