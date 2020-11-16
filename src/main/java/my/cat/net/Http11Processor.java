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
    private static final byte CR = (byte) '\r';
    private static final byte LF = (byte) '\n';
    private static final String CRLF = "\r\n";
    private static boolean isReadComplete = false;
    private static boolean isWriteComplete = false;

    @Override
    public synchronized boolean process(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (key.isReadable()) {
            ByteBuffer writeBuffer = socketBufferHandler.getWriteBuffer();
            socketChannel.read(writeBuffer);
            socketBufferHandler.configuredWriteBufferForWrite(false);
            int remaining = writeBuffer.remaining();
            byte[] bytes = new byte[remaining];
            int len = 0;
            for (int i = 0; i < remaining; i++) {
                if ((bytes[i] = writeBuffer.get()) == LF) {
                    isReadComplete = true;
                    break;
                }
                len++;
            }
            if (isReadComplete) {
                socketBufferHandler.reset();
                String msg = new String(bytes, 0, len, "UTF-8");
                System.err.println("received full msg: " + msg);
                ByteBuffer readBuffer = socketBufferHandler.getReadBuffer();
                socketBufferHandler.configuredReadBufferForWrite(true);
                readBuffer.put(msg.getBytes("UTF-8"));
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            } else {
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                writeBuffer.position(0);
                socketBufferHandler.configuredWriteBufferForWrite(true);
            }
        } else if (key.isWritable()) {
            ByteBuffer readBuffer = socketBufferHandler.getReadBuffer();
            socketBufferHandler.configuredReadBufferForWrite(false);
            if (readBuffer.hasRemaining()) {
                socketChannel.write(readBuffer);
            }
            if (readBuffer.hasRemaining()) {
                key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
            } else {
                isWriteComplete = true;
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            }
        }
        boolean state = isReadComplete & isWriteComplete;
        if (state) {
            isReadComplete = isWriteComplete = false;
        }
        return state;

    }
}
