package com.wggy.useful.part.io.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/***
 * created by wange on 2020/1/2 11:01
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        // 默默地丢弃收到的数据
        try {
            ((ByteBuf) msg).release(); // (3)
            // Do something with msg
        } finally {
            ReferenceCountUtil.release(msg);
        }*/

        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                // 打印消息内容
                System.out.println((char) in.readByte());
                System.out.flush();
            }
        } finally {
            // 释放消息
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
