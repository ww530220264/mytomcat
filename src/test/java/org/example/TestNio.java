package org.example;

import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestNio {
    @Test
    public void testServerSocketChannel() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(InetAddress.getByName("localhost"), 9001));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        Map<Object, Object> attr = new HashMap<>();
        attr.put("name", "wangwei");
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, attr);
        while (true) {
            if (selector.select(500) == 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                if (key.isValid() && key.isWritable()) {
                    handleWrite(key);
                }
                iterator.remove();
            }
        }
    }

    public void handleAccept(SelectionKey key) throws Exception {
        Object attachment = key.attachment();
        print(attachment);
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = channel.accept();
        print("accept: " + socketChannel.getRemoteAddress());
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ, buffer);
    }

    public void handleRead(SelectionKey key) throws Exception {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel channel = (SocketChannel) key.channel();
        print("read: " + channel.getRemoteAddress());
        int read = channel.read(buffer);
        if (-1 == read) {
            print("closed: " + channel.getRemoteAddress());
            channel.close();
        } else {
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    public void handleWrite(SelectionKey key) throws Exception {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel channel = (SocketChannel) key.channel();
        print("write: " + channel.getRemoteAddress());
        buffer.flip();
        channel.write(buffer);
        if (!buffer.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        buffer.compact();
    }


    public static void main(String[] args) {
        byte[] bytes = "abcde".getBytes();
        ByteBuffer wrap = ByteBuffer.wrap(bytes, 1, 2);
        print(wrap);
    }
    public static void print(Object o) {
        System.out.println(o);
    }
}
