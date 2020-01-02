package com.wggy.useful.part.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/***
 * created by wange on 2020/1/2 14:48
 */
public class ByteBufHeapDemo {
    public static void main(String[] args) {
        // 创建堆缓冲区
        ByteBuf buf = Unpooled.buffer(32);
        buf.writeBytes("netty".getBytes());

        // 检查是否具有支撑函数
        if (buf.hasArray()) {

            // 拿到支撑数组的引用
            byte[] array = buf.array();
            int offset = buf.readerIndex() + buf.arrayOffset();

            // 可读字节
            int length = buf.readableBytes();
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
