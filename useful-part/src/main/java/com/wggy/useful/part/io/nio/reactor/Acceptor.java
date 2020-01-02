package com.wggy.useful.part.io.nio.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/***
 * created by wange on 2020/1/2 16:43
 */
public class Acceptor implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {

        SocketChannel socketChannel;
        try {
            socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println(String.format("accpet %s", socketChannel.getRemoteAddress()));
                // 这里把客户端通道传给Handler，Handler负责接下来的事件处理
                new AsyncHandler(socketChannel, selector);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
