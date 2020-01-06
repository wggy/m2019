package com.wggy.useful.part.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/***
 * created by wange on 2019/12/31 16:03
 */
public class NonBlockingEchoClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(6333));

            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String userInput;
                while ((userInput = br.readLine()) != null) {
                    writeBuffer.put(userInput.getBytes());
                    writeBuffer.flip();
                    writeBuffer.rewind();

                    socketChannel.write(writeBuffer);
                    socketChannel.read(readBuffer);

                    writeBuffer.clear();
                    readBuffer.clear();
                    System.out.println("echo: " + userInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
