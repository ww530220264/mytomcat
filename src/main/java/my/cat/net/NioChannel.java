package my.cat.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class NioChannel implements ByteChannel, ScatteringByteChannel, GatheringByteChannel {

    protected static final ByteBuffer emptyBuf = ByteBuffer.allocate(0);
    protected SocketChannel sc;
    protected final SocketBufferHandler bufferHandler;

    public NioChannel(SocketChannel sc, SocketBufferHandler bufferHandler) {
        this.sc = sc;
        this.bufferHandler = bufferHandler;
    }

    public void reset() {
        bufferHandler.reset();
    }

    public void free() {
        bufferHandler.free();
    }

    public boolean flush(boolean block, Selector s, long timeout) {
        return true;
    }

    public void close(boolean force) throws IOException {
        if (isOpen() || force) {
            close();
        }
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
        checkInterruptStatus();
        return sc.write(srcs, offset, length);
    }

//    public Object getAttachment(){
//
//    }

    public void setIOChannel(SocketChannel IOChannel) {
        sc = IOChannel;
    }

    public int getOutboundRemaning() {
        return 0;
    }

    public boolean flushOutbound() {
        return false;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        checkInterruptStatus();
        return sc.write(src);
    }

    @Override
    public long write(ByteBuffer[] srcs) throws IOException {
        return write(srcs, 0, srcs.length);
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        return sc.read(dst);
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
        return sc.read(dsts, offset, length);
    }

    @Override
    public long read(ByteBuffer[] dsts) throws IOException {
        return read(dsts, 0, dsts.length);
    }

    @Override
    public boolean isOpen() {
        return sc.isOpen();
    }

    @Override
    public void close() throws IOException {
        getIOChannel().socket().close();
        getIOChannel().close();
    }

    public SocketChannel getIOChannel() {
        return sc;
    }

    public void checkInterruptStatus() throws IOException {
        if (Thread.interrupted()) {
            throw new IOException("channel is interrupted");
        }
    }
}
