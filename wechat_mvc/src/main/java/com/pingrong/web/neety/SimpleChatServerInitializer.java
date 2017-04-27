package com.pingrong.web.neety;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/3/27.
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8)); //解码方式
        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8)); //加码方式
        pipeline.addLast("handler", new SimpleChatServerHandler());
        //设置会话检测时间，读空闲时间（秒）写、全部
        pipeline.addLast("timeOut",new IdleStateHandler(5,5,10));

        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
    }
}
