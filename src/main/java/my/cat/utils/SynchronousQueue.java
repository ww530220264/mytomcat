package my.cat.utils;

public class SynchronousQueue<T> {

    private static final int DEFAULT_SIZE = 128;
    private Object[] queue;
    private int index = 0;
    private int removeIndex = 0;
    private int size;

    public SynchronousQueue() {
        this(DEFAULT_SIZE);
    }

    public SynchronousQueue(int defaultSize) {
        queue = new Object[defaultSize];
        size = defaultSize;
    }

    public synchronized boolean offer(T o) {
        queue[index++] = o;
        if (index == size) {
            index = 0;
        }
        if (index == removeIndex) {
            //扩容
            expand();
        }

        return true;
    }

    public synchronized T poll() {
        if (removeIndex == index) {
            return null;
        }

        T o = (T) queue[removeIndex++];
        queue[removeIndex] = null;
        if (removeIndex == size) {
            removeIndex = 0;
        }
        return o;
    }

    public void expand() {
        int newSize = size << 1;
        Object[] newQueue = new Object[size * 2];
        System.arraycopy(queue, index, newQueue, 0, size - index);
        System.arraycopy(queue, 0, newQueue, size - index, index);
        index = size;
        removeIndex = 0;
        queue = newQueue;
        size = newSize;
    }

    public synchronized int size() {
        int i = index - removeIndex;
        if (i < 0) {
            i += size;
        }
        return i;
    }

    public synchronized void clear() {
        queue = new Object[size];
        index = 0;
        removeIndex = 0;
    }
}
