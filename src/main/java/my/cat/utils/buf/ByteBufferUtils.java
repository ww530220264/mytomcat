package my.cat.utils.buf;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class ByteBufferUtils {

    private static final Method cleanerMethod;
    private static final Method cleanMethod;

    static {
        Method cleanerMethodGet;
        Method cleanMethodGet;
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(0);
            cleanerMethodGet = byteBuffer.getClass().getMethod("cleaner");
            Object cleanerObject = cleanerMethodGet.invoke(byteBuffer);
            cleanMethodGet = cleanerObject.getClass().getMethod("clean");
            cleanMethodGet.invoke(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
            cleanerMethodGet = null;
            cleanMethodGet = null;
        }
        cleanerMethod = cleanerMethodGet;
        cleanMethod = cleanMethodGet;
    }

    public static ByteBuffer expand(ByteBuffer in, int newSize) {
        if (in.capacity() > newSize) {
            return in;
        }
        boolean isDirect = false;
        ByteBuffer out;
        if (in.isDirect()) {
            out = ByteBuffer.allocateDirect(newSize);
            isDirect = true;
        } else {
            out = ByteBuffer.allocate(newSize);
        }
        in.flip();
        out.put(in);
        if (isDirect) {
            cleanDirectBuf(in);
        }
        return out;
    }

    public static void cleanDirectBuf(ByteBuffer buf){
        try {
            Object cleaner = cleanerMethod.invoke(buf);
            cleanMethod.invoke(cleaner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ByteBufferUtils(){}
}
