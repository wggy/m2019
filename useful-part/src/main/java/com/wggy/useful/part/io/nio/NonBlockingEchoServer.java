package com.wggy.useful.part.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 * created by wange on 2019/12/31 16:04
 */
public class NonBlockingEchoServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8002));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("NonBlokingEchoServer已启动，端口：8002");

            while (true) {
                selector.select();
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = readyKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();
                        System.out.println("NonBlokingEchoServer接受客户端的连接：" + socketChannel);
                        socketChannel.configureBlocking(false);

                        SelectionKey clientKey = socketChannel.register(selector, SelectionKey.OP_READ, SelectionKey.OP_WRITE);
                        // 分配缓存区
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }

                    // 可读
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);

                        System.out.println(client.getRemoteAddress() + " -> NonBlokingEchoServer：" + output.toString());

                        key.interestOps(SelectionKey.OP_WRITE);
                    }

                    // 可写
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);

                        System.out.println("NonBlokingEchoServer  -> " + client.getRemoteAddress() + "：" + output.toString());

                        output.compact();

                        key.interestOps(SelectionKey.OP_READ);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
