package com.wggy.useful.part.io.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/***
 * created by wange on 2020/1/2 10:24
 */
public class DatagramChannelEchoClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new DatagramChannelEchoClinetHandler());

        try {
            ChannelFuture f = b.bind(8007).sync();
            System.out.println("DatagramChannelEchoClient已启动，端口：8005");

            Channel channel = f.channel();
            ByteBuffer writeBuffer = ByteBuffer.allocate(32);

            try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
                String input;
                while ((input = stdIn.readLine()) != null) {
                    writeBuffer.put(input.getBytes());
                    writeBuffer.flip();
                    writeBuffer.rewind();

                    ByteBuf buf = Unpooled.copiedBuffer(writeBuffer);
                    channel.writeAndFlush(new DatagramPacket(buf, new InetSocketAddress("localhost", 8006)));
                    writeBuffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
