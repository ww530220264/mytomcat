package my.cat.component;

import my.cat.lifecycle.LifecycleListener;
import my.cat.lifecycle.LifecycleMBeanBase;
import my.cat.logging.Log;
import my.cat.logging.LogFactory;

import java.util.ArrayList;

public class StandardService extends LifecycleMBeanBase implements Service {

    private static final Log log = LogFactory.getLogger(StandardService.class);
    private ArrayList<LifecycleListener> lifecycleListeners = new ArrayList<>();
    private String name;
    private Engine engine;
    private Server server;

    public StandardService(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getName() {
        return name == null ? "" : name;
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public Engine getContainer() {
        return this.engine;
    }

    @Override
    public void setContainer(Engine engine) {
        this.engine = engine;
        engine.setService(this);
    }

    @Override
    protected void initInternal() {
        log.info("service init internal");
        super.initInternal();
        if (this.engine != null){
            this.engine.init();
        }
    }

    @Override
    protected void startInternal() {
        log.info("service start internal");
        super.startInternal();
        if (this.engine != null){
            this.engine.start();
        }
    }

    @Override
    protected void stopInternal() {
        if (this.engine != null){
            this.engine.stop();
        }
        super.stopInternal();
        log.info("service stop internal");
    }

    @Override
    protected void destoryInternal() {
        if (this.engine != null){
            this.engine.destory();
        }
        super.destoryInternal();
        log.info("service destory internal");
    }

    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycleListeners.add(lifecycleListener);
    }
}
