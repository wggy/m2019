package com.wggy.useful.part.rpc0;

import com.wggy.useful.part.rpc0.client.ApiService;
import com.wggy.useful.part.rpc0.client.ApiServiceImpl;
import com.wggy.useful.part.rpc0.client.RPCClient;
import com.wggy.useful.part.rpc0.server.RpcServer;
import com.wggy.useful.part.rpc0.server.RpcServerCenter;

import java.io.IOException;
import java.net.InetSocketAddress;

/***
 * created by wange on 2019/12/25 19:15
 */
public class RPCTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    RpcServer serviceServer = new RpcServerCenter(8088);
                    serviceServer.register(ApiService.class, ApiServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ApiService service = RPCClient.getRemoteProxyObj(ApiService.class, new InetSocketAddress("localhost", 8088));
        service.sayHi("test");
    }
}
