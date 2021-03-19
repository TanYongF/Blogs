# <center>个人博客代码托管仓库</center>

## 项目已成功部署至服务器 预览访问 http://imtyf.icu:9999/

## 一.项目技术

#### * 项目采用SpringBoot框架 前端采用比较流行的Semantic UI

#### * 分为控制层 业务层 数据持久层 严格按照SpringMVC架构模式开发.

#### * 后端持久层数据库采用MySQL

## 二.部署教程

#### 1.  从github中克隆项目 解压之后,用Java开发工具(推荐Idea)选择maven项目导入

#### 2. 修改开发环境(-dev)的配置文件中的数据库信息,确保连接的是自己的数据库  修改datasource下的jpa的ddl为create

#### 3. 运行springboot项目 即可自动在数据库中创建表结构  

#### 4. 手动在user表中插入一条数据  注意密码存储采用MD5加密, 可以使用java/lrm/utils/MD5工具类中的主方法转换.

#### 5. 博客首页访问地址: localhost:9999  博客后台登陆页面访问地址 localhost:9999/admin

#### 6. 后台依次添加分类，标签，博客内容  博客主页刷新即可更新.

##### *注:  如果要修改端口号  主配置文件即可修改

## 三.赞赏
此项目是根据[博客项目](https://github.com/ShaoxiongDu/ShaoxiongDu_Blog) 变体而来.但是做出了一下改变:
1. 权限采用了Spring Security框架
2. 后端接入了MongoDB的文档型数据库以便更快的查询数据
3. 完善了其他的一些功能
4. 修复了一些小的错误

