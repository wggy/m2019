package com.wggy.useful.part.rpc0.server;

import java.io.IOException;

/***
 * created by wange on 2019/12/25 19:07
 */
public interface RpcServer {

    void stop();

    void start() throws IOException;

    void register(Class serviceInterface, Class impl);

    boolean isRunning();

    int getPort();

}
