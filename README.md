# testDeploy
本项目是使用的 spring boot 3 写的一个简易的 CRUD 流程的项目，主要目的是为了测试 CI/CD 以及发布到云上的一切流程。
数据库使用的是 mysql，连接层使用 spring-data-jpa，构建工具是 gradle。

# 如何运行
- 1、克隆本项目到本地环境，然后使用 IDE 打开项目(推荐使用 jetbrains 的 IDEA)
- 2、自行安装好 mysql 8.0 以上的版本，并设置 root 密码为 123456 (否则就去项目中的 application-local.properties 文件中把密码改为自己的)
- 3、使用 IDEA 自带的 gradle 构建工具构建项目(或者在命令行使用 `gradle build` 命令)
- 4、配置 Application 的 active profiles 为 local , 然后运行。


## 此项目目前存在以下 RESTful 接口：
- post /api/test/search     搜索
- post /api/test            创建
- get /api/test/{name}      获取单个
- patch /api/test/{name}    修改
- delete /api/test/{name}   删除
