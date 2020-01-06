package com.wggy.useful.part.io.nio.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/***
 * created by wange on 2020/1/6 14:18
 */
public class SubReactor implements Runnable {

    private Selector selector;
    private volatile boolean register = false;
    private int num;

    SubReactor(Selector selector, int num) {
        this.selector = selector;
        this.num = num;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println(String.format("NO. %d SubReactor waitting for register...", num));
            while (!Thread.interrupted() && !register) {
                try {
                    if (selector.select() <= 0) {
                        continue;
                    }
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        dispatch(iterator.next());
                        iterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void dispatch(SelectionKey next) {
        Runnable r = (Runnable) next.attachment();
        if (r != null) {
            r.run();
        }
    }

    void registering(boolean register) {
        this.register = register;
    }
}
