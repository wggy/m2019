package com.wggy.useful.part.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * created by wange on 2019/12/31 15:47
 */
public class BlockingEchoServer {

    public static void main(String[] args) {
        try  {
            ServerSocket serverSocket = new ServerSocket(8001);
            System.out.println("服务器端口8001已启动");
            Socket socket = serverSocket.accept();
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null) {
                pw.println(msg);
                System.out.println("BlockingEchoServer -> " + socket.getRemoteSocketAddress() + ":" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
