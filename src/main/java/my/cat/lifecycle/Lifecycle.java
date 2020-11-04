package my.cat.lifecycle;

public interface Lifecycle {

    void init();
    void start();
    void stop();
    void destory();

    void addLifecycleListener(LifecycleListener lifecycleListener);
}
