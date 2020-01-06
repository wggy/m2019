package com.wggy.useful.part.io.nio.reactor.basic;

import java.io.IOException;

/***
 * created by wange on 2020/1/2 18:17
 */
public class BasicReactorDemo {

    public static void main(String[] args) {
        try {
            new Thread(new Reactor(6333)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
