package com.wggy.useful.part.io.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

/***
 * created by wange on 2020/1/2 10:19
 */
public class DatagramChannelEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DatagramPacket packet = (DatagramPacket) msg;
        System.out.println(packet.sender() + " -> Server :" + msg);

        DatagramPacket data = new DatagramPacket(packet.content(), packet.sender());
        ctx.write(data);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
