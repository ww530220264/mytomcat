package my.cat.net;

import my.cat.lifecycle.LifecycleBase;
import my.cat.lifecycle.LifecycleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangwei@huixiangtech.cn
 * @version 1.0
 * @className Connector
 * @description TODO
 * @date 2020/11/15-10:31
 **/
public class Connector extends LifecycleBase {
    private AbstractEndpoint<NioChannel> endpoint;
    private List<LifecycleListener> listeners = new ArrayList<>();
    private int port;

    public Connector(int port){
        this.port = port;
    }
    @Override
    protected void initInternal() {
        System.err.println("connector: " + this + " initInternal...");
        if (endpoint == null) {
            endpoint = new NioEndpoint();
            endpoint.setPort(port);
        }
        try {
            endpoint.bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startInternal() {
        System.err.println("connector: " + this + " startInternal...");
        try {
            endpoint.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void stopInternal() {
        System.err.println("connector: " + this + " stopInternal...");
    }

    @Override
    protected void destoryInternal() {
        System.err.println("connector: " + this + " destoryInternal...");
    }

    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        listeners.add(lifecycleListener);
    }
}
