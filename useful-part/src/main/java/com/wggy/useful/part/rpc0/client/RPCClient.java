package com.wggy.useful.part.rpc0.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/***
 * created by wange on 2019/12/25 19:13
 */
public class RPCClient<T> {

    public static <T> T getRemoteProxyObj(Class<?> serviceInterface, InetSocketAddress addr) {
        // 1.将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new ClzHandler(serviceInterface, addr));
    }

    private static class ClzHandler implements InvocationHandler {
        Class<?> serviceInterface;
        InetSocketAddress addr;

        ClzHandler(Class<?> serviceInterface, InetSocketAddress addr) {
            this.serviceInterface = serviceInterface;
            this.addr = addr;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                // 2.创建Socket客户端，根据指定地址连接远程服务提供者
                socket = new Socket();
                socket.connect(addr);

                // 3.将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeUTF(serviceInterface.getName());
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                output.writeObject(args);

                // 4.同步阻塞等待服务器返回应答，获取应答后返回
                input = new ObjectInputStream(socket.getInputStream());
                return input.readObject();
            } finally {
                if (socket != null) socket.close();
                if (output != null) output.close();
                if (input != null) input.close();
            }
        }
    }
}
