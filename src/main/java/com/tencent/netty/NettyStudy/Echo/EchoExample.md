# 主要内容
* netty的maven环境配置。
* 编写一个Echo的Server和Client协议；
* netty的拦截和处理异常；
* netty的一些基本概念。


## 环境配置
* Java；
* maven--netty jar包；
* eclipse

## 编写Echo Svr & client
### EchoSvr
#### 流程
* ServerBootstrap用来绑定和引导svr；
* NioEventLoopGroup 用来处理事件，比如接受新连接、接收数据、写数据等等；
* InetSocketAddress svr的ip:port,监听的地址和端口；
* childHandler 处理所有的连接请求；
* bind 绑定服务器

### 业务逻辑--EchoServerHandler
* 多个Channel Handler 用来对事件分离；
* 异常处理--exceptionCaught，比如有异常，关闭context。

### EchoClient
#### 流程
* Bootstrap引导客户端；
* EventLoopGroup 处理事件--接受连接、read/write数据；
* InetSocketAddress 配置svr的ip:port；
* ChannelHandler--具体的处理事件；
* Bootstrap.connect() 连接svr；
* closeFuture 关闭连接。

#### 业务逻辑
* channelActive() -- 连接后被调用；