## elephant 说明
#### 0 前言
项目是在学习[MyBatis-Plus](https://mp.baomidou.com/guide/)看到了优秀案例中的一个[mysiteforme](https://gitee.com/wanglingxiao/mysiteforme)
当时楼主正在学习docker-compose,觉得这个项目写的挺好,奉行拿来主义的原则,就拿来自己改造了一番。经原作者同意,便共享出来供大家参考借鉴。
### 1 主要改动点：

a、升级了springboot到2.1.3RELEASE版本
b、修改了包名以及部分类名
c、某些实体类的字段做了修改
d、升级mybatis-plus到3.1.0
e、使用docker-compose进行部署项目

### 安装docker-compose
```
curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

```

### 部署步骤  

下载 jdk-8u191-linux-x64.rpm

#### 1、创建镜像 
docker build -t destiny.com/elephant:v0.1 .
服务器的root目录下创建**Dockerfile**, Dockerfile存放在项目docker目录下的 Dockerfile-centos1.8

```
FROM centos
MAINTAINER destiny <xieyue86@163.com>
LABEL "rating"="Five Starts" "Class"="First Class"
RUN yum update -y && yum clean all -y &&  yum install -y zip unzip wget net-tools initscripts
ENV LANG en_US.utf8
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ADD ./jdk-8u191-linux-x64.rpm /root/jdk-8u191-linux-x64.rpm
RUN cd /root && yum install -y jdk-8u191-linux-x64.rpm
```

#### 2、执行脚本
下载项目 
```
git clone git@gitee.com:xieyue86/elephant.git
cd elephant 
## 打包项目,初始化项目路径，准备docker启动文件
sh script/start.sh
```
#### 3、启动项目

在项目路径下执行 cd elephant
启动项目 -d 后台启动项目
docker-compose up -d 
停止容器
docker-compose down 
项目访问,需要在本地 host 配置服务器的域名。修改`C:\Windows\System32\drivers\etc\host`文件, 添加以下内容:
```
项目服务器IP地址 elephant.destiny.com
```
浏览器访问`elephant.destiny.com`
登录账号/密码为:test/1

##### 删除docker容器
docker rm nginxserver elephant dbserver redisserver
##### 停止docker容器
docker rm nginxserver elephant dbserver redisserver
