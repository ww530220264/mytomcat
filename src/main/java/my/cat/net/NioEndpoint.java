package my.cat.net;

import lombok.Getter;
import my.cat.utils.SynchronousQueue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

public class NioEndpoint extends AbstractEndpoint {

    private ServerSocketChannel serverSocketChannel;
    private Acceptor acceptor;
    private Poller poller;
    private Map<SocketChannel, Processor> processorCache = new ConcurrentHashMap<>();

    public NioEndpoint() {
        executor = new ThreadPoolExecutor(
                10,
                10,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));
    }

    @Override
    public void bind() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port), 1024);
    }

    @Override
    public void start() throws Exception {
        poller = new Poller();
        new Thread(poller, "poller-thread").start();
        acceptor = new Acceptor();
        new Thread(acceptor, "acceptor-thread").start();
    }

    private class Acceptor extends AbstractEndpoint.Acceptor {
        @Override
        public void run() {
            SocketChannel socketChannel = null;
            try {
                while (true) {
                    socketChannel = NioEndpoint.this.serverSocketChannel.accept();
                    AcceptEvent acceptEvent = new AcceptEvent(socketChannel, poller);
                    poller.acceptQueue.offer(acceptEvent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socketChannel != null) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (serverSocketChannel != null) {
                    try {
                        serverSocketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class Poller implements Runnable {
        @Getter
        private Selector selector;
        private SynchronousQueue<AcceptEvent> acceptQueue;

        public Poller() {
            try {
                this.selector = Selector.open();
                acceptQueue = new SynchronousQueue<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean events() {
            int size = acceptQueue.size();
            boolean hasAcceptEvents = false;
            for (int i = 0; i < size; i++) {
                AcceptEvent event;
                if ((event = acceptQueue.poll()) != null) {
                    event.run();
                    hasAcceptEvents = true;
                }
            }
            return hasAcceptEvents;
        }

        @Override
        public void run() {
            while (true) {
                boolean hasAcceptEvents = events();
                int keyCount = 0;
                try {
                    if (hasAcceptEvents) {
                        keyCount = selector.selectNow();
                    } else {
                        keyCount = selector.select(500);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (keyCount > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    SelectionKey key;
                    if (keyIterator.hasNext()) {
                        key = keyIterator.next();
                        if (key.isReadable()) {
                            key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
                            process(key);
                        } else if (key.isWritable()) {
                            key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                            process(key);
                        }
                        keyIterator.remove();
                    }
                }
            }
        }

        private void process(SelectionKey key) {
            executor.execute(new ProcessTask(key));
        }
    }

    public class ProcessTask implements Runnable {
        private SelectionKey key;

        public ProcessTask(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            Processor processor = processorCache.computeIfAbsent((SocketChannel) key.channel(),
                    socketChannel -> new Http11Processor());
            try {
                boolean isCompleted = processor.process(key);
//                if (isCompleted) {
//                    processorCache.remove(key.channel());
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class AcceptEvent implements Runnable {
        private SocketChannel socket;
        private Poller poller;

        public AcceptEvent(SocketChannel socket, Poller poller) {
            this.socket = socket;
            this.poller = poller;
        }

        @Override
        public void run() {
            try {
                socket.configureBlocking(false);
                SelectionKey key = socket.register(poller.getSelector(), SelectionKey.OP_READ, null);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
