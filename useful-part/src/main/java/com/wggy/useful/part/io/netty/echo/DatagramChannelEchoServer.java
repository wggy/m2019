package com.wggy.useful.part.io.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/***
 * created by wange on 2020/1/2 10:17
 */
public class DatagramChannelEchoServer {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new DatagramChannelEchoServerHandler());

            ChannelFuture f = b.bind(8006).sync();
            System.out.println("DatagramChannelEchoServer服务器端口8006已启动");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
