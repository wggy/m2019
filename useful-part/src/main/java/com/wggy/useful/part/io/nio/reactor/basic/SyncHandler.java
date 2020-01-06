package com.wggy.useful.part.io.nio.reactor.basic;

import com.sun.org.apache.regexp.internal.RE;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/1/6 10:58
 */
public class SyncHandler implements Runnable {

    private SelectionKey selectionKey;
    private SocketChannel socketChannel;
    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(2048);

    private static final int READ = 0;
    private static final int WRITE = 1;
    private int status = READ;

    SyncHandler(SocketChannel sc, Selector sel) throws ClosedChannelException {
        this.socketChannel = sc;
        this.selector = sel;
        selectionKey = this.socketChannel.register(this.selector, 0);
        selectionKey.attach(this);

        selectionKey.interestOps(SelectionKey.OP_READ);
        this.selector.wakeup();
    }

    @Override
    public void run() {
        try {
            switch (status) {
                case READ:
                    read();
                    break;
                case WRITE:
                    write();
                    break;
                default:
            }
        } catch (IOException e) {
            // 这里的异常处理是做了汇总，常出的异常就是server端还有未读/写完的客户端消息，
            // 客户端就主动断开连接，这种情况下是不会触发返回-1的，
            // 这样下面read和write方法里的cancel和close就都无法触发，这样会导致死循环异常
            // （read/write处理失败，事件又未被cancel，因此会不断的被select到，不断的报异常）
            System.err.println("read或send时发生异常！异常信息：" + e.getMessage());
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e2) {
                System.err.println("关闭通道时发生异常！异常信息：" + e2.getMessage());
                e2.printStackTrace();
            }
        }

    }

    private void write() throws IOException {
        if (selectionKey.isValid()) {
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
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    private void read() throws IOException {
        if (selectionKey.isValid()) {
            System.out.println("服务端读取数据");
            readBuffer.clear();

            int count = socketChannel.read(readBuffer);
            if (count > 0) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("recived %s from %s",
                        new String(readBuffer.array()), socketChannel.getRemoteAddress()));
                status = WRITE;
                selectionKey.interestOps(SelectionKey.OP_WRITE); // 注册写方法
            } else {
                // 读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，
                // 否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，
                // 下次select会不停收到该事件.
                // 所以在这种场景下，（服务器程序）你需要关闭socketChannel并且取消key，最好是退出当前函数。
                // 注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
                selectionKey.cancel();
                socketChannel.close();
                System.out.println("read close");
            }
        }
    }
}
