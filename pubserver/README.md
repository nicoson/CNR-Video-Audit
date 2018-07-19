### pubserver 服务相关说明
pubserver 是集直播流转码，推流，切片，截帧为一体的分布式直播内容处理服务。

#### 配置文件修改

* 修改 apicd/apicd.conf 配置文件
* 修改 dispatcher/dispatcherd.conf 配置文件
* 修改 mgmtd/mgmtd.conf 配置文件
* 修改 worker/workerd.conf 配置文件


#### 运行
```bash
$ docker-compose up -d
```