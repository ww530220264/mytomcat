package my.cat.net;

import my.cat.utils.buf.ByteBufferUtils;

import java.nio.ByteBuffer;

public class SocketBufferHandler {
    private volatile boolean readBufferConfiguredForWrite = true;
    private volatile boolean writeBufferConfiguredForWrite = true;
    private volatile ByteBuffer readBuffer;
    private volatile ByteBuffer writeBuffer;
    public boolean isDirect;

    public SocketBufferHandler(boolean isDirect, int readBufferSize, int writeBufferSize) {
        this.isDirect = isDirect;
        if (isDirect) {
            this.readBuffer = ByteBuffer.allocateDirect(readBufferSize);
            this.writeBuffer = ByteBuffer.allocateDirect(writeBufferSize);
        } else {
            this.readBuffer = ByteBuffer.allocate(readBufferSize);
            this.writeBuffer = ByteBuffer.allocate(writeBufferSize);
        }
    }

    public void configuredReadBufferForWrite(boolean readBufferConfiguredForWrite) {
        setReadBufferConfiguredForWrite(readBufferConfiguredForWrite);
    }

    private void setReadBufferConfiguredForWrite(boolean readBufferConfiguredForWrite) {
        if (this.readBufferConfiguredForWrite != readBufferConfiguredForWrite) {
            if (readBufferConfiguredForWrite) {
                if (readBuffer.remaining() > 0){
                    readBuffer.compact();
                }else {
                    readBuffer.clear();
                }
            } else {
                readBuffer.flip();
            }
            this.readBufferConfiguredForWrite = readBufferConfiguredForWrite;
        }
    }

    public boolean isReadBufferEmpty() {
        if (readBufferConfiguredForWrite) {
            return readBuffer.position() == 0;
        } else {
            return readBuffer.remaining() == 0;
        }
    }

    public void configuredWriteBufferForWrite(boolean writeBufferConfiguredForWrite) {
        setWriteBufferConfiguredForWrite(writeBufferConfiguredForWrite);
    }

    private void setWriteBufferConfiguredForWrite(boolean writeBufferConfiguredForWrite) {
        if (this.writeBufferConfiguredForWrite != writeBufferConfiguredForWrite) {
            if (writeBufferConfiguredForWrite) {
                if (writeBuffer.remaining() > 0){
                    writeBuffer.compact();
                }else {
                    writeBuffer.clear();
                }
            } else {
                writeBuffer.flip();
            }
            this.writeBufferConfiguredForWrite = writeBufferConfiguredForWrite;
        }
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public boolean isWriteBufferWritable() {
        if (writeBufferConfiguredForWrite) {
            return writeBuffer.hasRemaining();
        } else {
            return writeBuffer.remaining() == 0;
        }
    }

    public boolean isWriteBufferEmpty() {
        if (writeBufferConfiguredForWrite) {
            return writeBuffer.position() == 0;
        } else {
            return writeBuffer.remaining() == 0;
        }
    }

    public void reset() {
        readBufferConfiguredForWrite = true;
        readBuffer.clear();
        writeBufferConfiguredForWrite = true;
        writeBuffer.clear();
    }

    public void expand(int newSize) {
        setReadBufferConfiguredForWrite(true);
        ByteBufferUtils.expand(readBuffer, newSize);
        setWriteBufferConfiguredForWrite(true);
        ByteBufferUtils.expand(writeBuffer, newSize);
    }

    public void free() {
        if (isDirect) {
            ByteBufferUtils.cleanDirectBuf(readBuffer);
            ByteBufferUtils.cleanDirectBuf(writeBuffer);
        }
    }
}
