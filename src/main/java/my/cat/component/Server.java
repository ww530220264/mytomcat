package my.cat.component;

import my.cat.lifecycle.Lifecycle;

public interface Server extends Lifecycle {

    int getPort();
    void setPort(int port);
    String getAddress();
    void setAddress(String address);
    void addService(Service service);
    Service findService(String name);
}
