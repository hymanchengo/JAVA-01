package com.hymanting.geekjavatrain.socket.week3homework2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author hxchen
 * @Date 2021/1/20
 */
public class Server8801 {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8801);
        while (true) {
            Socket conSocket = socket.accept();
            service(conSocket);
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello, nio";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
