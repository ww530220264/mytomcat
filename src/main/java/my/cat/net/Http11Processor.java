package my.cat.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author wangwei@huixiangtech.cn
 * @version 1.0
 * @className Http11Processor
 * @description TODO
 * @date 2020/11/15-17:33
 **/
public class Http11Processor extends AbstractProcessor {
    private SocketBufferHandler socketBufferHandler = new SocketBufferHandler(false, 65535, 65535);
    @Override
    public void process(SelectionKey key) throws IOException {
        boolean isReadComplete = false;
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (key.isReadable()) {
            ByteBuffer writeBuffer = socketBufferHandler.getWriteBuffer();
            int readBytes = socketChannel.read(writeBuffer);
            System.err.println("readBytes: " + readBytes);
        } else if (key.isWritable()) {
            ByteBuffer readBuffer = socketBufferHandler.getReadBuffer();
            if (readBuffer.hasRemaining()) {
                int writeBytes = socketChannel.write(readBuffer);
                System.err.println("write bytes: " + writeBytes);
            }
        }
    }
}
