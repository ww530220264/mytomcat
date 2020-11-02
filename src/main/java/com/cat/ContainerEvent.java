package com.cat;

import java.util.EventObject;

public class ContainerEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    private final String type;
    private final Object data;

    public ContainerEvent(Container container, String type, Object data) {
        super(container);
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    @Override
    public Container getSource() {
        return (Container) super.getSource();
    }
}
