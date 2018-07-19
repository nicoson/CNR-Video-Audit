### 部署说明书

#### 前置环境

* 安装好docker,Java,maven (建议修改maven的软件源，不然maven打包非常慢,建议docker使用七牛镜像加速)
* 准备环境

```bash
$ cd video 
$ make prepare
```

#### 修改配置文件(重要，非常重要)
* mysql 数据库配置（推荐用 mysql workbench 把 db/qiniu 这个数据库还原)
* 修改 pub 服务的相关配置文件
* 把所有项目下的 resoures目录下的spring-config.properties中的ip "23.59.204.170" 改为要部署的服务器的ip

#### 打包并且运行
* 打包
```bash
$ cd video
$ make
```

* 运行

```bash
$ cd pubserver
$ docker-compose up -d # -d 是后台运行
$ docker run -d -p 0.0.0.0:8080:8080 video --log-opt --max-size="50M" --max-file="10"
```

* 浏览器访问 http://ip:8080/QiniuWebSite/ ,用户：admin,Q0001,Q0002,密码：同用户名

