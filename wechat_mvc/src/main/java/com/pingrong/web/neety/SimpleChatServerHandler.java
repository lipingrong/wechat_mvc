package com.pingrong.web.neety;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Administrator on 2017/3/27.
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<Object> { // (1)

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 新连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        }
        channels.add(ctx.channel());
    }

    /**
     * 断连
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
        }
        channels.remove(ctx.channel());
    }

    /**
     * 接收消息
     * @param ctx
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object s) throws Exception { // (4)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
                //channel.writeAndFlush("[" + incoming.remoteAddress() + "]在和服务器交流" + "\n");
            } else {
                System.out.println(incoming.remoteAddress() + "发来消息：" + s);
                //channel.writeAndFlush("收到你消息了，什么事？" + "\n");
            }
        }
    }

    /**
     * 连接提示
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
        incoming.writeAndFlush("我知道你已经连接了~我感觉到你了" + "\n"); //发送消息
    }

    /**
     * 离线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
    }

    /**
     * 错误处理
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 所有请求都会经过这个方法，可用来检测心跳idle
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE){
                ChannelFuture channelFuture = ctx.writeAndFlush("即将断开连接！");
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        ctx.channel().close();
                    }
                });
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }
}
