package com.wggy.useful.part.io.nio.reactor.client;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/***
 * created by wange on 2020/1/6 11:21
 */
public class Connector implements Runnable {

    private Selector selector;
    private SocketChannel socketChannel;

    public Connector(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            if (this.socketChannel.finishConnect()) {
                // 这里连接完成（与服务端的三次握手完成）
                System.out.println(String.format("connected to %s", socketChannel.getRemoteAddress()));
                // 连接建立完成后，接下来的动作交给Handler去处理（读写等）
                new Handler(socketChannel, selector);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
