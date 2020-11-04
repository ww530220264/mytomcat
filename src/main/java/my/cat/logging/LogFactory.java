package my.cat.logging;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.util.ServiceLoader;

public class LogFactory {
    private static final LogFactory singleton = new LogFactory();
    private final Constructor<? extends Log> discoveredConstructor;

    private LogFactory() {
        FileSystems.getDefault();
        ServiceLoader<Log> loader = ServiceLoader.load(Log.class);
        Constructor<? extends Log> m = null;
        for (Log log : loader) {
            try {
                m = log.getClass().getConstructor(String.class);
                break;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        discoveredConstructor = m;
    }

    private Log getInstance(String name) {
        if (discoveredConstructor == null){
            return JDKLog.getInstance(name);
        }
        try {
            return discoveredConstructor.newInstance(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Log getInstance(Class clazz){
        return getInstance(clazz.getName());
    }

    private static LogFactory getLogFactory(){
        return singleton;
    }

    public static Log getLogger(String name){
        return getLogFactory().getInstance(name);
    }

    public static Log getLogger(Class clazz){
        return getLogFactory().getInstance(clazz.getName());
    }
}
