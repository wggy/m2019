package com.wggy.useful.part.io.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/***
 * created by wange on 2019/12/31 17:15
 */
public class AioEchoClient {
    public static void main(String[] args) {

        try {
            AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8003));

            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String input;
                while ((input = br.readLine()) != null) {
                    writeBuffer.put(input.getBytes());
                    // 翻转缓冲区，缓冲区限制大小limit设置为position，position、mark重置
                    writeBuffer.flip();
                    writeBuffer.rewind();

                    socketChannel.write(writeBuffer);

                    socketChannel.read(readBuffer);
                    writeBuffer.clear();
                    readBuffer.clear();
                    System.out.println("echo: " + input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
