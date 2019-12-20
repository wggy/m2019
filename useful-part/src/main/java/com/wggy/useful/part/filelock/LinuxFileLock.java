package com.wggy.useful.part.filelock;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/***
 * created by wange on 2019/12/19 16:42
 */
public class LinuxFileLock {
    public static void writeFileLock(String content, String path) {
        FileLock lock = null;
        try {
            FileChannel channel = new FileOutputStream(path, true).getChannel();
            while (true) {
                try {
                    lock = channel.lock();
                    if (lock != null && lock.isValid()) {
                        break;
                    }
                } catch (Exception e) {
                    Thread.sleep(10);
                }
            }
            channel.write(ByteBuffer.wrap(content.getBytes()));
            lock.release();
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
