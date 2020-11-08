package my.cat.component;

import my.cat.container.Container;
import my.cat.lifecycle.LifecycleListener;
import my.cat.lifecycle.LifecycleMBeanBase;
import my.cat.logging.Log;
import my.cat.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StandardEngine extends LifecycleMBeanBase implements Engine {

    private final static Log log = LogFactory.getLogger(StandardEngine.class);
    private List<LifecycleListener> lifecycleListeners = new ArrayList<>();
    private CopyOnWriteArrayList<Container> childs = new CopyOnWriteArrayList<>();
    private Service service;

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void addChild(Container container) {
        childs.add(container);
        
    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    protected void initInternal() {
        log.info("engine init internal");
        super.initInternal();
    }

    @Override
    protected void startInternal() {
        log.info("engine start internal");
        super.startInternal();
    }

    @Override
    protected void stopInternal() {
        log.info("engine stop internal");
        super.stopInternal();
    }

    @Override
    protected void destoryInternal() {
        log.info("engine destory internal");
        super.destoryInternal();
    }

    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycleListeners.add(lifecycleListener);
    }
}
