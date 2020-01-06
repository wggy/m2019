package com.wggy.useful.part.io.nio.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/***
 * created by wange on 2020/1/6 11:57
 */
public class Acceptor implements Runnable {

    private static final int CORE_NUMS = Runtime.getRuntime().availableProcessors();
    private ServerSocketChannel serverSocketChannel;
    private int next = 0;

    private Selector[] selectors = new Selector[CORE_NUMS];
    private SubReactor[] subReactors = new SubReactor[CORE_NUMS];

    Acceptor(ServerSocketChannel ssc) throws IOException {
        Thread[] threads = new Thread[CORE_NUMS];
        this.serverSocketChannel = ssc;
        for (int i=0; i<CORE_NUMS; i++) {
            selectors[i] = Selector.open();
            subReactors[i] = new SubReactor(selectors[i], i);
            threads[i] = new Thread(subReactors[i]);
            threads[i].start();
        }
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        try {
            socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println(String.format("accpet %s", socketChannel.getRemoteAddress()));
                socketChannel.configureBlocking(false);
                // 注意一个selector在select时是无法注册新事件的，因此这里要先暂停下select方法触发的程序段，
                // 下面的weakup和这里的setRestart都是做这个事情的，具体参考SubReactor里的run方法
                subReactors[next].registering(true);
                selectors[next].wakeup();
                SelectionKey selectionKey = socketChannel.register(selectors[next], SelectionKey.OP_READ);
                selectors[next].wakeup();

                // 本次事件注册完成后，需要再次触发select的执行，
                // 因此这里Restart要在设置回false（具体参考SubReactor里的run方法）
                subReactors[next].registering(false);
                // 绑定Handler
                selectionKey.attach(new AsyncHandler(socketChannel, selectors[next], next));
                if (++next == selectors.length) {
                    next = 0; // 越界后重新分配
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
