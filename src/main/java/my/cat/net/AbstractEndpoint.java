package my.cat.net;

import java.util.concurrent.Executor;

public abstract class AbstractEndpoint<S> {
    protected enum BindState {
        UNBOUND, BOUND_ON_INIT, BOUND_ON_START, SOCKET_CLOSED_ON_STOP
    }

    public abstract static class Acceptor implements Runnable{
        public enum AcceptorState{
            NEW,RUNNING,PAUSED,ENDED;
        }
        protected volatile AcceptorState state = AcceptorState.NEW;
        public final AcceptorState getState() {return state;}
        private String threadName;
        public final void setThreadName(String threadName) {this.threadName = threadName;}
        public final String getThreadName(String threadName) {return threadName;}
    }

    protected volatile boolean running = false;
    protected volatile boolean paused = false;
    protected volatile boolean internalExecutor = true;
//    private volatile LimitLatch connectionLimitLatch = null;
    private int acceptorThreadCount = 1;
    protected int acceptorThreadPriority = Thread.NORM_PRIORITY;
    private int maxConnections = 10000;
    private Executor executor = null;
    private int port;
    private int minSpareThreads = 10;
    private int maxThreads; // worker threads
    private int maxHeaderCount = 100;
    private int maxKeepAliveRequests=100;



}
