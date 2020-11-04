package my.cat.logging;

import java.util.logging.*;

public class JDKLog implements Log {

    public final Logger logger;
    private static final String SIMPLE_FMT = "java.util.logging.SimpleFormatter";
    private static final String FORMATTER = "org.apache.juli.formatter";

    static {
        try {
            String cname = System.getProperty(FORMATTER, SIMPLE_FMT);
            Formatter formatter = (Formatter) Class.forName(cname).getConstructor().newInstance();
            Logger root = Logger.getLogger("");
            Handler[] handlers = root.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    handler.setFormatter(formatter);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private JDKLog(String name) {
        logger = Logger.getLogger(name);
    }

    @Override
    public boolean isErrorEnable() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public boolean isDebugEnable() {
        return logger.isLoggable(Level.FINE);
    }

    @Override
    public boolean isWarnEnable() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isInfoEnable() {
        return logger.isLoggable(Level.INFO);
    }

    @Override
    public boolean isFatalEnable() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public boolean isTraceEnable() {
        return logger.isLoggable(Level.FINER);
    }

    @Override
    public void trace(Object message) {
        log(Level.FINER, String.valueOf(message), null);
    }

    @Override
    public void trace(Object message, Throwable t) {
        log(Level.FINER, String.valueOf(message), t);
    }

    @Override
    public void info(Object message) {
        log(Level.INFO, String.valueOf(message), null);
    }

    @Override
    public void info(Object message, Throwable t) {
        log(Level.INFO, String.valueOf(message), t);
    }

    @Override
    public void debug(Object message) {
        log(Level.FINE, String.valueOf(message), null);
    }

    @Override
    public void debug(Object message, Throwable t) {
        log(Level.FINE, String.valueOf(message), t);
    }

    @Override
    public void warn(Object message) {
        log(Level.WARNING, String.valueOf(message), null);
    }

    @Override
    public void warn(Object message, Throwable t) {
        log(Level.WARNING, String.valueOf(message), t);
    }

    @Override
    public void fatal(Object message) {
        log(Level.SEVERE, String.valueOf(message), null);
    }

    @Override
    public void fatal(Object message, Throwable t) {
        log(Level.SEVERE, String.valueOf(message), t);
    }

    @Override
    public void error(Object message) {
        log(Level.SEVERE, String.valueOf(message), null);
    }

    @Override
    public void error(Object message, Throwable t) {
        log(Level.SEVERE, String.valueOf(message), t);
    }

    private void log(Level level, String message, Throwable t) {
        if (logger.isLoggable(level)) {
            Throwable dummyException = new Throwable();
            StackTraceElement[] locations = dummyException.getStackTrace();
            String cname = "unknown";
            String method = "unknown";
            if (locations != null && locations.length > 2) {
                StackTraceElement caller = locations[2];
                cname = caller.getClassName();
                method = caller.getMethodName();
            }
            if (t == null) {
                logger.logp(level, cname, method, message);
            } else {
                logger.logp(level, cname, method, message, t);
            }
        }
    }

    public static Log getInstance(String name){
        return new JDKLog(name);
    }
}
