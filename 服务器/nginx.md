# nginx version1.14.2实操

## 安装（源码安装）和使用

### 解释一下源码安装

​	安装源码包，需要我们把源代码编译成可执行的二进制文件。源码包的编译用到了Linux系统里的编译器。常见的源码包一般都是用C语言开发的，因为C语言是Linux上最标准的程序语言。Linux上的C语言编译器称为gcc，利用它可以把C语言编译成可执行的二进制文件。所以，如果机器上没有安装gcc，就无法编译源码，可以使用命令yum install -y gcc来完成安装。

安装源码包通常需要以下3个步骤：

    ./configure:这一步可以定制功能，加上相应的选项即可，具体有什么选项可以通过命令./configure –help来查看。这一步会自动检测你的Linux系统与相关的套件是否有编译该源码包时所需要的库，因为一旦缺少某个库，就不能完成编译。只有检测通过后，才回生成Makefile文件。

    make:使用这个命令，会根据Makefile文件中预设的参数进行编译，这一步其实就是gcc在工作了

    make install：这一步是安装步骤，用于创建相关软件的存放目录和配置文件。

对于以上3个步骤，并不是所有的源码包文件都一样，也就是说，源码包的安装并没有标准的安装步骤。这就需要拿到源码包后，进入目录，找到相关的帮助文档（通常，会以INSTALL或者READEME为文件名）。

其中常用的配置选项有	`--prefix=PREFIX`，它的意思是定义软件包的安装路径。

生成Makefile后，进行编译

make

make install

### 安装步骤

* 下载地址`	http://nginx.org/download/nginx-1.14.2.tar.gz`
* 上传到服务器上解压`tar -zxvf nginx-1.14.2.tar.gz`
* 配置安装路径 `./configure --prefix=/home/dengdongdong/nginx/nginx-1.14.2`
* 使用root身份安装pcre         `sudo yum install pcre-devel`
* http_gzip_module需要使用zlib library，所以使用root身份安装zlib  `sudo yum install zlib-devel`
* 编译并且安装 `make & make install`

### 使用步骤

* 执行./sbin/nginx启动nginx的时候报错，错误如下:

  ```shell
  bind() to 0.0.0.0:80 failed (13: Permission denied)
  ```

  原因：Linux只有root用户可以使用1024一下的端口

  解决方法1：将端口改为1024以上

  解决方法2：以root身份启动 `sudo ./sbin/nginx`


* 停止 `../sbin/nginx -s stop`
* 修改了配置文件后不用重启，使用 `../sbin/nginx -s reload` 重新加载配置文件
* `ss -ntlp` 命令查看监听的端口


#### rewrite执行过程

##### 测试情景1配置如下

```properties
server {
    rewrite_log  on;
	listen       80;
	server_name  www.dongdong.com;
	rewrite     "^/aaa\.html$" /bbb.html;
	rewrite    "^/bbb\.html$" /ccc.html;
	location / {
		root html;
	}
	location /ccc.html{
	   rewrite "^/ccc\.html$" /ddd.html;
	   rewrite "^/ddd\.html$" /eee.html;
	}
	location /eee.html{
	  rewrite "^/eee\.html$" /fff.html;
	}
}
```

执行 curl www.dongdong.com/aaa.html

查看日志可以看出rewrite的执行过程

```
2018/12/18 14:59:39 [notice] 8820#0: *25 "^/aaa\.html$" matches "/aaa.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 rewritten data: "/bbb.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 "^/bbb\.html$" matches "/bbb.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 rewritten data: "/ccc.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 "^/ccc\.html$" matches "/ccc.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 rewritten data: "/ddd.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 "^/ddd\.html$" matches "/ddd.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 rewritten data: "/eee.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 "^/eee\.html$" matches "/eee.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 14:59:39 [notice] 8820#0: *25 rewritten data: "/fff.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
```

##### 测试情景2 配置如下

```properties
server {
	listen       80;
	server_name  www.dongdong.com;
	rewrite_log on;

	rewrite     "^/aaa\.html$" /bbb.html;
	rewrite    "^/bbb\.html$" /ccc.html;
	location / {
		root html;
	}
	location /ccc.html{
	   rewrite "^/ccc\.html$" /ddd.html last;
	   rewrite "^/ddd\.html$" /eee.html;
	}
	location /ddd.html{
	  rewrite "^/ddd\.html$" /eee.html;
	}
}
```

执行 curl www.dongdong.com/aaa.html

查看日志可以看出rewrite的执行过程

```
2018/12/18 15:10:11 [notice] 9019#0: *26 "^/aaa\.html$" matches "/aaa.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 rewritten data: "/bbb.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 "^/bbb\.html$" matches "/bbb.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 rewritten data: "/ccc.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 "^/ccc\.html$" matches "/ccc.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 rewritten data: "/ddd.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 "^/ddd\.html$" matches "/ddd.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:10:11 [notice] 9019#0: *26 rewritten data: "/eee.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
```

rewirte last:直接返回不在执行后面的rewrite，但是会重新执行server 里边的内容。



##### 测试情景3 配置如下

```properties
server {
	listen       80;
	server_name  www.dongdong.com;
	rewrite_log on;
	
	rewrite     "^/aaa\.html$" /bbb.html;
	rewrite    "^/bbb\.html$" /ccc.html;
	location / {
		root html;
	}
	location /ccc.html{
	   rewrite "^/ccc\.html$" /ddd.html break;
	   rewrite "^/ddd\.html$" /eee.html;
	}
	location /ddd.html{
	  rewrite "^/ddd\.html$" /eee.html;
	}
}
```

执行 curl www.dongdong.com/aaa.html

查看日志可以看出rewrite的执行过程

```
2018/12/18 15:15:56 [notice] 9124#0: *28 "^/aaa\.html$" matches "/aaa.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:15:56 [notice] 9124#0: *28 rewritten data: "/bbb.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:15:56 [notice] 9124#0: *28 "^/bbb\.html$" matches "/bbb.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:15:56 [notice] 9124#0: *28 rewritten data: "/ccc.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:15:56 [notice] 9124#0: *28 "^/ccc\.html$" matches "/ccc.html", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
2018/12/18 15:15:56 [notice] 9124#0: *28 rewritten data: "/ddd.html", args: "", client: 127.0.0.1, server: www.dongdong.com, request: "GET /aaa.html HTTP/1.1", host: "www.dongdong.com"
```

rewirte break:直接返回不在执行后面的rewrite，不会再server里边的内容。





