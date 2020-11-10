package org.example;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TestAio {

    @Test
    public void testAioServer() throws Exception {
        AsynchronousServerSocketChannel ascc = AsynchronousServerSocketChannel.open();
        ascc.bind(new InetSocketAddress(9001), 3);
        Map<Object, Object> acceptAttachment = new HashMap<>();
        acceptAttachment.put("name", "wangwei");
        ascc.accept(acceptAttachment, new CompletionHandler<AsynchronousSocketChannel, Map<Object, Object>>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Map<Object, Object> acceptAttachment) {
                print(acceptAttachment);
                acceptAttachment.put("age", 18);
                try {
                    print("got client connection: " + socketChannel.getRemoteAddress());
                    ascc.accept(acceptAttachment, this);
                    // read
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    Map<Object, Object> readAttachment = new HashMap<>();
                    readAttachment.put("socketAddr", socketChannel.getRemoteAddress());
                    socketChannel.read(readBuffer, readAttachment, new CompletionHandler<Integer, Map<Object, Object>>() {
                        @Override
                        public void completed(Integer readBytes, Map<Object, Object> readAttachment) {
                            if (readBytes == -1){
                                try {
                                    socketChannel.close();
                                    return;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }finally {
                                    if (socketChannel != null){
                                        try {
                                            socketChannel.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            print("read bytes: " + readBytes);
                            readBuffer.flip();
                            print("buffer remaining: " + readBuffer.remaining());
                            print(readAttachment);
                            print(readBuffer);
                            byte[] bytes = new byte[readBuffer.remaining()];
                            readBuffer.get(bytes);
                            readBuffer.clear();
                            socketChannel.read(readBuffer,readAttachment,this);
                            ByteBuffer writeBuffer = ByteBuffer.wrap(bytes);
                            socketChannel.write(writeBuffer, null, new CompletionHandler<Integer, Object>() {
                                @Override
                                public void completed(Integer writeBytes, Object attachment) {
                                    print("write bytes: " + writeBytes);
                                    print("write remaining: " + writeBuffer.remaining());
                                    print(attachment);
                                }

                                @Override
                                public void failed(Throwable exc, Object attachment) {
                                    exc.printStackTrace();
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable exc, Map<Object, Object> attachment) {
                            exc.printStackTrace();
                        }
                    });
                    // write
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Map<Object, Object> attachment) {
                exc.printStackTrace();
            }
        });
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    public void print(Object o) {
        System.out.println(o);
    }
}
