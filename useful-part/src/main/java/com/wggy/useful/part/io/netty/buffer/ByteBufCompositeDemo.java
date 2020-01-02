package com.wggy.useful.part.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/***
 * created by wange on 2020/1/2 15:04
 */
public class ByteBufCompositeDemo {
    public static void main(String[] args) {
        ByteBuf heapBuf = Unpooled.buffer(32);
        heapBuf.writeBytes("heapBuf".getBytes());

        ByteBuf directBuf = Unpooled.directBuffer(32);
        directBuf.writeBytes("directBuf".getBytes());

        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer(32);
        compositeByteBuf.addComponents(heapBuf, directBuf);

        // 检查是否支撑数组，不支撑数组，则是符合缓冲区
        if (!compositeByteBuf.hasArray()) {
            for (ByteBuf buf: compositeByteBuf) {
                // 计算第一个字节的偏移量
                int offset = buf.readerIndex();

                int length = buf.readableBytes();
                byte[] bytes = new byte[length];
                buf.getBytes(offset, bytes);
                printBuffer(bytes, offset, length);
            }
        }

    }

    private static void printBuffer(byte[] array, int offset, int len) {
        System.out.println("array：" + array);
        System.out.println("array->String：" + new String(array));
        System.out.println("offset：" + offset);
        System.out.println("len：" + len);
    }
}
