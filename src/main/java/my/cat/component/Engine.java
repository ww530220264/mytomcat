package my.cat.component;

import my.cat.container.Container;
import my.cat.lifecycle.Lifecycle;

public interface Engine extends Lifecycle {
    Service getService();
    void setService(Service service);

    void addChild(Container container);
    Container findChild(String name);
}
