package com.tencent.netty.NettyStudy.PlainAndNettyOIOAndNIO;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Listing 4.4  of <i>Netty in Action</i>
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class NettyNioServer {

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
             .channel(NioServerSocketChannel.class)
             .localAddress(new InetSocketAddress(port))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) 
                     throws Exception {
                     ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    	 @Override
                    	    public void channelRead(ChannelHandlerContext ctx,
                    	        Object msg) {
                    	        ByteBuf in = (ByteBuf) msg;
                    	        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
                    	        ctx.write(in);
                    	    }
                    	 
                    	 @Override
                    	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                    	        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                    	    }
                    	 
//                         @Override
//                         public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                             ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
//                         }
                         
                         @Override
                         public void exceptionCaught(ChannelHandlerContext ctx,
                             Throwable cause) {
                             cause.printStackTrace();
                             ctx.close();
                         }
                     });
                 }
             });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
    
    public static void main(String[] args) throws Exception {
		NettyNioServer nettyNioServer = new NettyNioServer();
		nettyNioServer.server(6666);
	}
}

