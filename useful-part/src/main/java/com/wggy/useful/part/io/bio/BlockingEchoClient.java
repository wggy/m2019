package com.wggy.useful.part.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/***
 * created by wange on 2019/12/31 15:46
 */
public class BlockingEchoClient {
    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.err.println("用法: java BlockingEchoClient <host name> <port number>");
//            System.exit(1);
//        }
        String hostName = "localhost";
        int portNumber = 8001;

        try (Socket sock = new Socket(hostName, portNumber);
             PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                pw.println(userInput);
                System.out.println("echo : " + br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void echo(String hostName, int portNumber) {
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("不明主机，主机名为： " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("不能从主机中获取I/O，主机名为：" +
                    hostName);
            System.exit(1);
        }
    }
}
