package my.cat.component;

import my.cat.lifecycle.Lifecycle;

public interface Engine extends Lifecycle {
    Service getService();
    void setService(Service service);
}
