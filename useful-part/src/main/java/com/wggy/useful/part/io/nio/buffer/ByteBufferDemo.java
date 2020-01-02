package com.wggy.useful.part.io.nio.buffer;


import java.nio.ByteBuffer;

/***
 * created by wange on 2020/1/2 14:05
 */
public class ByteBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        System.out.println(">>>>>>>>>>>初始化缓冲区<<<<<<<<<<<");
        printBuffer(buffer);

        buffer.put("netty".getBytes());
        System.out.println(">>>>>>>>>>put netty<<<<<<<<<<<");
        printBuffer(buffer);

        buffer.flip();
        System.out.println(">>>>>>>>>flip切换重读模式<<<<<<<<<<<");
        printBuffer(buffer);

        System.out.println(">>>>>>>>>>读取缓冲区数据<<<<<<<<<<<<<");
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        printBuffer(buffer);

        System.out.println(">>>>>>>>>>compact<<<<<<<<<<<<<<<<<");
        buffer.compact();
        printBuffer(buffer);

        System.out.println(">>>>>>>>>>>>clear<<<<<<<<<<<<<<");
        buffer.clear();
        printBuffer(buffer);
    }

    private static void printBuffer(ByteBuffer buffer) {
        System.out.println("mark: " + buffer.mark());
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.mark());
    }
}
