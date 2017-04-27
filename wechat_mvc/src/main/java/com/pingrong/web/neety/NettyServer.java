package com.pingrong.web.neety;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2017/3/27.
 */
public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //处理线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // (1)
        //工作线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            //设置线程池
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3) 设置工厂类
                    .childHandler(new SimpleChatServerInitializer())  //(4)//设置管道
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)serverSocketChannel的设置，链接缓存池的大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)socketChannel设置，维持链接活跃，清理死链接
                    //.childOption(ChannelOption.TCP_NODELAY, true); // (6)socketChannel设置，关闭延迟发送消息
            //TCP参数

            System.out.println("SimpleChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            /**
             * 释放资源
             */
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("SimpleChatServer 关闭了");
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 9092;
        }
        new NettyServer(port).run();

    }
}
