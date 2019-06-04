## 常用命令

### 防火墙

* centos7查看防火墙的状态
  * firewall-cmd --state
* 停止防火墙
  * systemctl stop firewalld.service
* 禁止开机启动
  * systemctl disable firewalld.service

### 查看文件编码

file -i catalina.2018-07-30.log

### 进程

#### ps -ef|grep

ps命令将某个进程显示出来

grep命令是查找

中间的`|` 是管道命令 是指ps命令与grep同时执行

ps是Linux下最常用的也是非常强大的进程查看命令

grep命令是查找，是一种强大的文本搜索工具，它能使用正则表达式搜索文本，并把匹配的行打印出来。

grep全称是Global Regular Expression Print，表示全局正则表达式版本，它的使用权限是所有用户。

以下这条命令是检查java 进程是否存在：ps -ef |grep java

字段含义如下：
UID       PID       PPID        C     STIME       TTY     TIME         CMD

zzw      14124   13991      0     00:38      pts/0    00:00:00    grep --color=auto dae

**UID      ：程序被该 UID 所拥有**

**PID      ：就是这个程序的 ID **

**PPID    ：则是其上级父程序的ID**

**C          ：CPU使用的资源百分比**

**STIME ：系统启动时间**

**TTY     ：登入者的终端机位置**

**TIME   ：使用掉的CPU时间。**

**CMD   ：所下达的是什么指令**



ps -ef|grep -v grep |grep elasticsearch |awk '{print $2}'

查看elasticsearch 的pid，并且忽略grep ，将查询出来的pid打印出来



### 查看语言包

locale

```shel
LANG=en_US.UTF-8
LC_CTYPE="en_US.UTF-8"
LC_NUMERIC="en_US.UTF-8"
LC_TIME="en_US.UTF-8"
LC_COLLATE="en_US.UTF-8"
LC_MONETARY="en_US.UTF-8"
LC_MESSAGES="en_US.UTF-8"
LC_PAPER="en_US.UTF-8"
LC_NAME="en_US.UTF-8"
LC_ADDRESS="en_US.UTF-8"
LC_TELEPHONE="en_US.UTF-8"
LC_MEASUREMENT="en_US.UTF-8"
LC_IDENTIFICATION="en_US.UTF-8"
LC_ALL=
```

### 查看ip

ifconfig 查看内网ip

curl ident.me 查看外网ip 

## 脚本

### #!

```java
在Unix中，凡是被#!注释的，统统是加载器(解释器)的路径。
比如:
#!/bin/bash
#上面的意思是说，把下面的字符统统传递给/bin/bash
echo "Hello Boy!"
....
其实这个就是声明用哪个程序来运行本文件
```

### 赋值

```shell
用` `,把命令括起来，然后赋值给变量
dir=`pwd`
```

### >和>>区别

```shell
>：创建
>>: 追加
```
