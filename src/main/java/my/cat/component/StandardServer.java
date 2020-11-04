package my.cat.component;

import my.cat.lifecycle.LifecycleListener;
import my.cat.lifecycle.LifecycleMBeanBase;
import my.cat.logging.Log;
import my.cat.logging.LogFactory;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class StandardServer extends LifecycleMBeanBase implements Server {

    private CopyOnWriteArrayList<Service> services = new CopyOnWriteArrayList<>();
    private ArrayList<LifecycleListener> lifecycleListeners = new ArrayList<>();
    private int port;
    private String address;
    private static final Log log = LogFactory.getLogger(StandardServer.class);

    @Override
    protected void initInternal() {
        log.info("server init internal");
        super.initInternal();
        for (Service service:services){
            service.init();
        }
    }

    @Override
    protected void startInternal() {
        log.info("server start internal");
        super.startInternal();
        for (Service service:services){
            service.start();
        }
    }

    @Override
    protected void stopInternal() {
        for (Service service:services){
            service.stop();
        }
        log.info("server stop internal");
        super.stopInternal();
    }

    @Override
    protected void destoryInternal() {
        for (Service service:services){
            service.destory();
        }
        log.info("server destory internal");
        super.destoryInternal();
    }

    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycleListeners.add(lifecycleListener);
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void addService(Service service) {
        services.add(service);
        service.setServer(this);
    }

    @Override
    public Service findService(String name) {
        for (Service service : services) {
            if (name.equals(service.getName())) {
                return service;
            }
        }
        return null;
    }
}
