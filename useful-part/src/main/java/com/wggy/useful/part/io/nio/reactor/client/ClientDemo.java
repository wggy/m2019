package com.wggy.useful.part.io.nio.reactor.client;

import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/1/6 11:38
 */
public class ClientDemo {
    public static void main(String[] args) {
        new Thread(new NioClient()).start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new NioClient()).start();
    }
}
