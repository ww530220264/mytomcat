package com.cat;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port, 2, InetAddress.getByName("192.168.1.107"));
        while (true) {
            Socket socket = serverSocket.accept();
            InetAddress client = socket.getInetAddress();
            String clientAddress = client.getHostAddress();
            System.out.println("got connect: " + clientAddress);
            InputStream ios = socket.getInputStream();
            byte[] bytes = new byte[ios.available()];
            ios.read(bytes);
            String input = new String(bytes, "UTF-8");
//            System.out.println("receive data from client: " + clientAddress + ": \n" + input);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String content = "<body><h1 style=\"color:red\">Hello</h1></body>";
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Length: " + content.length());
            writer.println();
            writer.println(content);
//            writer.close();
        }
    }

    public static void main(String[] args) throws IOException {
        MyServer myServer = new MyServer();
        myServer.start(9999);
    }
}
