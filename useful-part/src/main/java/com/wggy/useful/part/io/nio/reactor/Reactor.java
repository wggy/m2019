package com.wggy.useful.part.io.nio.reactor;

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

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor(serverSocketChannel, selector));
    }


    @Override
    public void run() {
        
        while (true) {
            try {
                selector.select();

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
        Runnable r = (Runnable) next.attachment();
        if (r != null) {
            long be = System.currentTimeMillis();
            r.run();
            long ed = System.currentTimeMillis();
            System.out.println(">>>>>>>>>cost time: " + (ed - be));
        }
    }
}
