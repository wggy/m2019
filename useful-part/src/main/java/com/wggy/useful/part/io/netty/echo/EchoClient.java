package com.wggy.useful.part.io.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/***
 * created by wange on 2020/1/2 9:20
 */
public class EchoClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new EchoClientHandler());

        try {
            ChannelFuture f = b.connect(new InetSocketAddress("localhost", 8009)).sync();
            Channel channel = f.channel();
            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String input;
                while ((input = br.readLine()) != null) {
                    writeBuffer.put(input.getBytes());
                    writeBuffer.flip();
                    writeBuffer.rewind();

                    ByteBuf buf = Unpooled.copiedBuffer(writeBuffer);
                    channel.writeAndFlush(buf);
                    writeBuffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
