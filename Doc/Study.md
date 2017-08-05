# Netty的关键概念
* Netty 中所有的IO 操作都是异步执行的, 不知道操作是否成功，通过注册监听的方式来通知。Netty通过Futures来达到这种目的。
* Netty 是一个非阻塞、事件驱动的网络框架。
* Bootstrap用来连接远程主机；ServerBootstrap用来绑定本地端口。
* ServerBootstrap 有两个EventLoopGroup，一个用来接收请求，一个用来处理请求。用于并发请求过来的时候，接收连接和处理连接不会共享资源，减少互相干扰，成为瓶颈。
* EventLoop是一个事件循环线程，EventLoopGroup 是一个事件循环集合。
* ChannelPipeline是一个handler的容器，决定了Handler处理数据的顺序。
* ChannelInboundHandler 是从ChannelPipeline 的头部开始；ChannelOutboundHandler 是从ChannelPipeline 的尾部开始。
* 接收消息解码--将字节码转换成对象；  发放消息编码--将对象转换成字节码。


# 传输数据
* 传输协议；
* NIO零复制，直接在内核空间操作，不用将数据拷贝到jvm堆栈。