package com.wggy.useful.part.io.nio.reactor.mainsub;

import java.io.IOException;

/***
 * created by wange on 2020/1/6 15:02
 */
public class MainSubReactorDemo {
    public static void main(String[] args) throws IOException {
        new Thread(new Reactor()).start();
    }
}
