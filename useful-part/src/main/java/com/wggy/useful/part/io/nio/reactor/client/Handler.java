package com.wggy.useful.part.io.nio.reactor.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * created by wange on 2020/1/6 11:30
 */
public class Handler implements Runnable {
    private SelectionKey selectionKey;
    private SocketChannel socketChannel;

    private static final int READ = 0;
    private static final int SEND = 1;

    private int status = READ;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer sendBuffer = ByteBuffer.allocate(2048);
    private AtomicInteger counter = new AtomicInteger();

    public Handler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = this.socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_WRITE);
        selector.wakeup();
    }


    @Override
    public void run() {
        try {
            switch (status) {
                case READ:
                    read();
                    break;
                case SEND:
                    send();
                    break;
                default:
            }
        } catch (IOException e) {
            // 这里的异常处理是做了汇总，同样的，客户端也面临着正在与服务端进行写/读数据时，
            // 突然因为网络等原因，服务端直接断掉连接，这个时候客户端需要关闭自己并退出程序
            System.err.println("send或read时发生异常！异常信息：" + e.getMessage());
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e2) {
                System.err.println("关闭通道时发生异常！异常信息：" + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }

    private void send() throws IOException {
        if (selectionKey.isValid()) {
            sendBuffer.clear();
            int count = counter.incrementAndGet();
            if (count <= 10) {
                sendBuffer.put(String.format("msg is %s", count).getBytes());
                sendBuffer.flip(); // 切换到读模式，用于让通道读到buffer里的数据
                socketChannel.write(sendBuffer);
                // 则再次切换到读，用以接收服务端的响应
                status = READ;
                selectionKey.interestOps(SelectionKey.OP_READ);
            } else {
                selectionKey.cancel();
                socketChannel.close();
            }
        }
    }

    private void read() throws IOException {
        if (selectionKey.isValid()) {
            readBuffer.clear();
            socketChannel.read(readBuffer);
            System.out.println(String.format("Server -> Client: %s", new String(readBuffer.array())));
            // 收到服务端的响应后，再继续往服务端发送数据
            status = SEND;
            selectionKey.interestOps(SelectionKey.OP_WRITE); // 注册写事件
        }
    }
}
