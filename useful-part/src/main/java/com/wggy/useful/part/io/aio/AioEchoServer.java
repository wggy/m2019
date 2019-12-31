package com.wggy.useful.part.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/***
 * created by wange on 2019/12/31 17:15
 */
public class AioEchoServer {

    public static void main(String[] args) {
        AsynchronousServerSocketChannel serverSocketChannel;


        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8003));

            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            System.out.println("AsyncEchoServer已启动，端口：8003");


            while (true) {
                Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
                AsynchronousSocketChannel socketChannel = future.get();
                System.out.println("AsyncEchoServer接受客户端的连接：" + socketChannel);

                ByteBuffer buffer = ByteBuffer.allocate(100);
                while (socketChannel.read(buffer).get() != -1) {
                    buffer.flip();
                    socketChannel.write(buffer).get();
                    System.out.println("AsyncEchoServer  -> "
                            + socketChannel.getRemoteAddress() + "：" + buffer.toString());

                    if (buffer.hasRemaining()) {
                        buffer.compact();
                    } else {
                        buffer.clear();
                    }
                }
                socketChannel.close();
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
