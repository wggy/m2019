package com.wggy.useful.part.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/***
 * created by wange on 2020/1/2 14:30
 */
public class ByteBufDemo {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(32);
        System.out.println("=========初始化缓冲区===========");
        printBuf(buf);

        System.out.println("=========写入netty==============");
        buf.writeBytes("netty".getBytes());
        printBuf(buf);

        System.out.println("==========读取数据=============");
        while (buf.isReadable()) {
            System.out.println(Byte.toString(buf.readByte()));
        }
        printBuf(buf);

        System.out.println("==========do discard==========");
        buf.discardReadBytes();
        printBuf(buf);

        System.out.println("============do clear===========");
        buf.clear();
        printBuf(buf);


    }

    private static void printBuf(ByteBuf buf) {
        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());
        System.out.println("capacity：" + buf.capacity());
    }
}
