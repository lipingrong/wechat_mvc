package com.pingrong.web.neety;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2017/3/27.
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object s) throws Exception {
        System.out.println(s);
    }
}
