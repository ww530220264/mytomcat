package my.cat.logging;

public interface Log {
    boolean isErrorEnable();
    boolean isDebugEnable();
    boolean isWarnEnable();
    boolean isInfoEnable();
    boolean isFatalEnable();
    boolean isTraceEnable();

    public void trace(Object message);
    public void trace(Object message,Throwable t);
    public void info(Object message);
    public void info(Object message,Throwable t);
    public void debug(Object message);
    public void debug(Object message,Throwable t);
    public void warn(Object message);
    public void warn(Object message,Throwable t);
    public void fatal(Object message);
    public void fatal(Object message,Throwable t);
    public void error(Object message);
    public void error(Object message,Throwable t);
}
