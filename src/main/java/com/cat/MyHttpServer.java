package com.cat;

import com.cat.servlet.http.MyHttpServletRequest;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String SHUT_DOWN = "/SHUT_DOWN";
    public volatile boolean shutdown = false;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("<Usage: please add arguement port>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        MyHttpServer server = new MyHttpServer();
        try {
            server.start(port);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("server started failer");
        }
    }

    public void start(int port) throws Exception {
        System.err.println("start server.....");
        ServerSocket serverSocket = new ServerSocket(port, 10, InetAddress.getByName("localhost"));
        System.err.println("started server.....");
        while (!shutdown) {
            Socket socket = serverSocket.accept();
            System.err.println("got connection from " + socket.getInetAddress().getHostAddress());
            try (
                    InputStream ios = socket.getInputStream();
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
            ) {
                MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(ios);
                // 解析inputstream到request
                myHttpServletRequest.parse();
                System.err.println(myHttpServletRequest.getUri());
                // 写内容
                StringBuilder contentBuilder = new StringBuilder("");
                contentBuilder.append("<body>你好</body>");
                String content = contentBuilder.toString();
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Length: " + content.length());
                writer.println("Content-Type: text/html;charset=utf-8");// 避免中文乱码
                writer.println();
                writer.println(content);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
