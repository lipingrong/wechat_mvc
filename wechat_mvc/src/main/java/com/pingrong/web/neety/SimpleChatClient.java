package com.pingrong.web.neety;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/27.
 */
public class SimpleChatClient {
    public static void main(String[] args) throws Exception{
        new SimpleChatClient("127.0.0.1", 9092).run();
    }

    private final String host;
    private final int port;

    public SimpleChatClient (String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        //工作
        EventLoopGroup group = new NioEventLoopGroup();
        try {
        //服务类
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group) //设置线程
                    .channel(NioSocketChannel.class) //socket工厂
                    .handler(new SimpleChatClientInitializer()); //管道类
            Channel channel = bootstrap.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
