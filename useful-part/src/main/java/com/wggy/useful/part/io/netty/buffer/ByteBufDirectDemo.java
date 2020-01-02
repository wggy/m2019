package com.wggy.useful.part.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/***
 * created by wange on 2020/1/2 14:44
 */
public class ByteBufDirectDemo {
    public static void main(String[] args) {

        // 创建直接缓冲区
        ByteBuf buf = Unpooled.directBuffer(32);

        buf.writeBytes("netty".getBytes());
        if (!buf.hasArray()) {
            int offset = buf.readerIndex();
            int length = buf.readableBytes();

            byte[] array = new byte[length];
            buf.getBytes(offset, array);
            printBuffer(array, offset, length);
        }

    }

    private static void printBuffer(byte[] array, int offset, int len) {
        System.out.println("array：" + array);
        System.out.println("array->String：" + new String(array));
        System.out.println("offset：" + offset);
        System.out.println("len：" + len);
    }
}
