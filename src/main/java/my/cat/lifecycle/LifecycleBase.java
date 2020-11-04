package my.cat.lifecycle;

public abstract class LifecycleBase implements Lifecycle {

    @Override
    public void init() {
        initInternal();
    }

    protected abstract void initInternal();

    @Override
    public void start() {
        startInternal();
    }

    protected abstract void startInternal();

    @Override
    public void stop() {
        stopInternal();
    }

    protected abstract void stopInternal();

    @Override
    public void destory() {
        destoryInternal();
    }

    protected abstract void destoryInternal();
}
