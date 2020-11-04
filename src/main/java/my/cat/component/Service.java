package my.cat.component;

import my.cat.lifecycle.Lifecycle;

public interface Service extends Lifecycle {

    String getName();
    Server getServer();
    void setServer(Server server);
    Engine getContainer();
    void setContainer(Engine engine);

}
