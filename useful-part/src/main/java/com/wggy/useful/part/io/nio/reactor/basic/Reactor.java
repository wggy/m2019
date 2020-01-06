package com.wggy.useful.part.io.nio.reactor.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 * created by wange on 2020/1/2 16:35
 */
public class Reactor implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    Reactor(int port) throws IOException {
        // 创建多路复用器
        selector = Selector.open();

        // 创建server管道
        serverSocketChannel = ServerSocketChannel.open();

        // 绑定服务器ip和端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // selector模式下，所有通道非阻塞
        serverSocketChannel.configureBlocking(false);

        // Reactor模式入口，最初给channel注册上去的事件都是accptor
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 附加回调对象
        sk.attach(new Acceptor(serverSocketChannel, selector));
    }


    @Override
    public void run() {
        
        while (true) {
            try {
                // 就绪时间到达前，该动作阻塞
                selector.select();

                // 获取到达的就绪事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    dispatch(it.next());
                }
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void dispatch(SelectionKey next) {
        // 获取附加对象
        Runnable r = (Runnable) next.attachment();
        if (r != null) {
            long be = System.currentTimeMillis();
            r.run();
            long ed = System.currentTimeMillis();
            System.out.println(">>>>>>>>>cost time: " + (ed - be));
        }
    }
}
