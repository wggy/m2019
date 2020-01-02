package com.wggy.useful.part.io.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * created by wange on 2020/1/2 9:12
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " -> Server :" + msg);
        // 写消息到管道
        ctx.write(msg);// 写消息
        ctx.flush(); // 刷新消息

        // 上面两个方法等同于 ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
