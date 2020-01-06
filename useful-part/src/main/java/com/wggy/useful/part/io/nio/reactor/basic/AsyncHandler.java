package com.wggy.useful.part.io.nio.reactor.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/1/2 17:17
 */
public class AsyncHandler implements Runnable {
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private Selector selector;
    private int status = READ;

    private final static int READ = 0; // 读取就绪
    private final static int SEND = 1; // 响应就绪
    private final static int PROCESSING = 2; // 处理中

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
    // 开启线程数为5的异步处理线程池
    private static final ExecutorService workers = Executors.newFixedThreadPool(5);

    AsyncHandler(SocketChannel c, Selector sel) throws IOException {
        this.socketChannel = c; // 接收客户端连接
        this.socketChannel.configureBlocking(false); // 置为非阻塞模式
        selectionKey = this.socketChannel.register(sel, 0); // 将该客户端注册到selector
        selectionKey.interestOps(SelectionKey.OP_READ); // 连接已完成，接下来就是读取动作

        selectionKey.attach(this); // 附加处理对象，当前是Handler对象
        this.selector = sel;
        this.selector.wakeup();
    }


    @Override
    public void run() {
        switch (status) {
            case READ:
                read();
                break;
            case SEND:
                send();
            default:
                break;
        }
    }

    private void send() {
        if (selectionKey.isValid()) {
            status = PROCESSING;
            workers.execute(this::sendWorker);
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    private void sendWorker() {
        try {
            writeBuffer.clear();
            writeBuffer.put(String
                    .format("recived %s from %s", new String(readBuffer.array()), socketChannel.getRemoteAddress())
                    .getBytes());
            writeBuffer.flip();

            // write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束
            int count = socketChannel.write(writeBuffer);

            if (count < 0) {
                // 同上，write场景下，取到-1，也意味着客户端断开连接
                selectionKey.cancel();
                socketChannel.close();
                System.out.println("send close");
            }

            // 没断开连接，则再次切换到读
            status = READ;
        } catch (IOException e) {
            System.err.println("异步处理send业务时发生异常！异常信息：" + e.getMessage());
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e1) {
                System.err.println("异步处理send业务关闭通道时发生异常！异常信息：" + e.getMessage());
            }
        }
    }

    private void readWorker() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("client -> Server： %s", new String(readBuffer.array())));
        status = SEND;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        this.selector.wakeup();
    }

    private void read() {
        if (selectionKey.isValid()) {
            readBuffer.clear();

            // read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
            try {
                int count = socketChannel.read(readBuffer);
                if (count > 0) {
                    status = PROCESSING;
                    workers.execute(this::readWorker);
                } else {
                    // 读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，
                    // 否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，
                    // 下次select会不停收到该事件。
                    // 所以在这种场景下，需要关闭socketChannel并且取消key，最好是退出当前函数。
                    // 注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，
                    // 就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
                    selectionKey.cancel();
                    socketChannel.close();
                    System.out.println("read closed");
                }
            } catch (IOException e) {
                System.err.println("处理read业务时发生异常！异常信息：" + e.getMessage());
                selectionKey.cancel();
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    System.err.println("处理read业务关闭通道时发生异常！异常信息：" + e.getMessage());
                }
            }
        }
    }
}
